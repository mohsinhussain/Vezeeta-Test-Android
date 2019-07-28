/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohsin.vezeeta.features.characters

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.annotation.StringRes
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohsin.vezeeta.core.platform.BaseFragment
import com.mohsin.vezeeta.R
import com.mohsin.vezeeta.features.characters.MovieFailure.ListNotAvailable
import com.mohsin.vezeeta.core.exception.Failure
import com.mohsin.vezeeta.core.exception.Failure.NetworkConnection
import com.mohsin.vezeeta.core.exception.Failure.ServerError
import com.mohsin.vezeeta.core.extension.failure
import com.mohsin.vezeeta.core.extension.invisible
import com.mohsin.vezeeta.core.extension.observe
import com.mohsin.vezeeta.core.extension.viewModel
import com.mohsin.vezeeta.core.extension.visible
import com.mohsin.vezeeta.core.navigation.EndlessRecyclerViewScrollListener
import com.mohsin.vezeeta.core.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_movies.emptyView
import kotlinx.android.synthetic.main.fragment_movies.movieList
import javax.inject.Inject

class CharacterListFragment : BaseFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var moviesAdapter: MoviesAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var offSet = 0
    var nameStartsWith = ""

    private var searchView: SearchView? = null

    private lateinit var moviesViewModel: MoviesViewModel

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        setHasOptionsMenu(true)

        moviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }


    }

    private fun resetListView(){
        offSet = 0
        moviesViewModel.loadCharacters(offSet, nameStartsWith)
        moviesAdapter.collection.clear()
        moviesAdapter.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.search)
                .actionView as SearchView
        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(activity!!.componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE

        searchView!!.setOnCloseListener {
            nameStartsWith = ""
            resetListView()
            return@setOnCloseListener false
        }

        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("onQueryTextChange", query)
                nameStartsWith = query
                resetListView()
                // filter recycler view when query submitted
//                viewAdapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
//                viewAdapter.getFilter().filter(query)
                nameStartsWith = query
                resetListView()
                Log.i("onQuerySubmitted", query)
                return false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }


    private fun initializeView() {
//        movieList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        movieList.layoutManager = linearLayoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                offSet = offSet + 20
                loadMoviesList()
            }
        }
        // Adds the scroll listener to RecyclerView
        movieList.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        movieList.adapter = moviesAdapter
        moviesAdapter.clickListener = { movie, navigationExtras ->
                    navigator.showMovieDetails(activity!!, movie, navigationExtras) }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
//        moviesViewModel.loadMovies()
        moviesViewModel.loadCharacters(offSet, nameStartsWith)
    }

    private fun renderMoviesList(movies: List<MovieView>?) {

        moviesAdapter.collection.addAll(movies.orEmpty())
        moviesAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_character_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
//        movieList.invisible()
//        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
