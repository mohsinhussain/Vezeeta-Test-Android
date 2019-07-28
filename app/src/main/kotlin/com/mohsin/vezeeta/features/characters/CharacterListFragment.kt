
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohsin.vezeeta.core.platform.BaseFragment
import com.mohsin.vezeeta.R
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
import kotlinx.android.synthetic.main.fragment_characters.emptyView
import kotlinx.android.synthetic.main.fragment_characters.movieList
import javax.inject.Inject

class CharacterListFragment : BaseFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var characterListAdapter: CharacterListAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var offSet = 0
    var nameStartsWith = ""

    private var searchView: SearchView? = null

    private lateinit var charactersViewModel: CharactersViewModel

    override fun layoutId() = R.layout.fragment_characters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setHasOptionsMenu(true)
        charactersViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }


    }

    private fun resetListView(){
        offSet = 0
        charactersViewModel.loadCharacters(offSet, nameStartsWith)
        characterListAdapter.collection.clear()
        characterListAdapter.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.options_menu, menu)

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

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("onQueryTextChange", query)
                nameStartsWith = query
                resetListView()
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
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
        val linearLayoutManager = LinearLayoutManager(activity)
        movieList.layoutManager = linearLayoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                offSet += 20
                loadMoviesList()
            }
        }

        movieList.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        movieList.adapter = characterListAdapter
        characterListAdapter.clickListener = { movie, navigationExtras ->
                    navigator.showMovieDetails(activity!!, movie, navigationExtras) }
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        charactersViewModel.loadCharacters(offSet, nameStartsWith)
    }

    private fun renderMoviesList(characters: List<CharacterView>?) {
        characterListAdapter.collection.addAll(characters.orEmpty())
        characterListAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}
