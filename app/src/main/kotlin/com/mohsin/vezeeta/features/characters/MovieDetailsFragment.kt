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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsin.vezeeta.core.platform.BaseFragment
import com.mohsin.vezeeta.R
import com.mohsin.vezeeta.features.characters.MovieFailure.NonExistentMovie
import com.mohsin.vezeeta.core.exception.Failure
import com.mohsin.vezeeta.core.exception.Failure.NetworkConnection
import com.mohsin.vezeeta.core.exception.Failure.ServerError
import com.mohsin.vezeeta.core.extension.*
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_CHARACTER = "param_character"

        fun forMovie(movie: MovieView): MovieDetailsFragment {
            val movieDetailsFragment = MovieDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_CHARACTER, movie)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }

    @Inject lateinit var comicsAdapter: ResourceAdapter
    @Inject lateinit var seriesAdapter: ResourceAdapter
    @Inject lateinit var storiesAdapter: ResourceAdapter
    @Inject lateinit var eventsAdapter: ResourceAdapter
    @Inject lateinit var movieDetailsAnimator: MovieDetailsAnimator

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun layoutId() = R.layout.fragment_character_details

    lateinit var character: MovieView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let { movieDetailsAnimator.postponeEnterTransition(it) }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(comics, ::renderComicsList)
            failure(failure, ::handleFailure)
        }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(series, ::renderSeriesList)
            failure(failure, ::handleFailure)
        }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(stories, ::renderStoriesList)
            failure(failure, ::handleFailure)
        }

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(events, ::renderEventsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        character = arguments!![PARAM_CHARACTER] as MovieView

//        if (firstTimeCreated(savedInstanceState)) {
//            movieDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_CHARACTER) as MovieView).id)
//        } else {
//        movieDetailsAnimator.scaleUpView(moviePlay)
        movieDetailsAnimator.cancelTransition(moviePoster)
        val character: MovieView = arguments!![PARAM_CHARACTER] as MovieView
        moviePoster.loadFromUrl(character.thumbnail.path+"."+character.thumbnail.extension)
        renderMovieDetails(MovieDetailsView(character.id, character.name, character.thumbnail.path+"."+character.thumbnail.extension, character.description, "", "", "", ""))

        loadComics()
        loadStories()
        loadSeries()
        loadEvents()

        for(link in character.urls){
            if(link.type.contentEquals("detail")){
                detail.visibility = ViewGroup.VISIBLE
                detail.setOnClickListener{
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(link.url)
                    startActivity(openURL)
                }
            }

            if(link.type.contentEquals("comiclink")){
                comicLinks.visibility = ViewGroup.VISIBLE
                comicLinks.setOnClickListener{
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(link.url)
                    startActivity(openURL)
                }
            }


            if(link.type.contentEquals("wiki")){
                wiki.visibility = ViewGroup.VISIBLE
                wiki.setOnClickListener{
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(link.url)
                    startActivity(openURL)
                }
            }


        }


//        }
    }



    private fun initializeView() {
        comicsList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        comicsList.adapter = comicsAdapter

        storiesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        storiesList.adapter = storiesAdapter

        seriesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        seriesList.adapter = seriesAdapter

        eventsList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        eventsList.adapter = eventsAdapter

//        comicsAdapter.clickListener = { movie, navigationExtras ->
//            navigator.showMovieDetails(activity!!, movie, navigationExtras) }
    }




    override fun onBackPressed() {
        movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
//        if (moviePlay.isVisible())
//            movieDetailsAnimator.scaleDownView(moviePlay)
//        else
//            movieDetailsAnimator.cancelTransition(moviePoster)
    }

    private fun loadComics() {
        showProgress()
        movieDetailsViewModel.loadComics(character.id, "comics")
    }

    private fun loadStories() {
        showProgress()
        movieDetailsViewModel.loadStories(character.id, "stories")
    }

    private fun loadSeries() {
        showProgress()
        movieDetailsViewModel.loadSeries(character.id, "series")
    }

    private fun loadEvents() {
        showProgress()
        movieDetailsViewModel.loadEvents(character.id, "events")
    }

    private fun renderComicsList(resources: List<ResourceEntity>?) {
        comicsAdapter.collection.addAll(resources.orEmpty())
        comicsAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun renderSeriesList(resources: List<ResourceEntity>?) {
        seriesAdapter.collection.addAll(resources.orEmpty())
        seriesAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun renderStoriesList(resources: List<ResourceEntity>?) {
        storiesAdapter.collection.addAll(resources.orEmpty())
        storiesAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun renderEventsList(resources: List<ResourceEntity>?) {
        eventsAdapter.collection.addAll(resources.orEmpty())
        eventsAdapter.notifyDataSetChanged()
        hideProgress()
    }

    private fun renderMovieDetails(movie: MovieDetailsView?) {
        movie?.let {
            with(movie) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
                    it.toolbar.title = name
                }
                nameTextView.text = name
                descTextView.text = desciption
//                moviePlay.setOnClickListener { movieDetailsViewModel.playMovie(trailer) }
            }
        }
        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)

//        movieDetailsAnimator.scaleUpView(moviePlay)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> { notify(R.string.failure_network_connection); close() }
//            is ServerError -> { notify(R.string.failure_server_error); close() }
//            is NonExistentMovie -> { notify(R.string.failure_character_non_existent); close() }
//            is Failure.ResourceError -> { notify(R.string.failure_resource_non_existent); close() }
        }
    }
}
