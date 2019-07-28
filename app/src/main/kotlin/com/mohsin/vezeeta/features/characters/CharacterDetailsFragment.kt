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

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsin.vezeeta.core.platform.BaseFragment
import com.mohsin.vezeeta.R
import com.mohsin.vezeeta.core.exception.Failure
import com.mohsin.vezeeta.core.exception.Failure.NetworkConnection
import com.mohsin.vezeeta.core.extension.*
import kotlinx.android.synthetic.main.custom_layout.*
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import javax.inject.Inject
import android.view.ViewGroup



class CharacterDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_CHARACTER = "param_character"

        fun forMovie(character: CharacterView): CharacterDetailsFragment {
            val movieDetailsFragment = CharacterDetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(PARAM_CHARACTER, character)
            movieDetailsFragment.arguments = arguments

            return movieDetailsFragment
        }
    }

    @Inject lateinit var comicsAdapter: ResourceAdapter
    @Inject lateinit var seriesAdapter: ResourceAdapter
    @Inject lateinit var storiesAdapter: ResourceAdapter
    @Inject lateinit var eventsAdapter: ResourceAdapter
    @Inject lateinit var dialogAdapter: DialogAdapter
    @Inject lateinit var characterDetailAnimator: CharacterDetailAnimator

    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel

    override fun layoutId() = R.layout.fragment_character_details

    lateinit var character: CharacterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activity?.let { characterDetailAnimator.postponeEnterTransition(it) }

        characterDetailsViewModel = viewModel(viewModelFactory) {
            observe(comics, ::renderComicsList)
            failure(failure, ::handleFailure)
        }

        characterDetailsViewModel = viewModel(viewModelFactory) {
            observe(series, ::renderSeriesList)
            failure(failure, ::handleFailure)
        }

        characterDetailsViewModel = viewModel(viewModelFactory) {
            observe(stories, ::renderStoriesList)
            failure(failure, ::handleFailure)
        }

        characterDetailsViewModel = viewModel(viewModelFactory) {
            observe(events, ::renderEventsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        character = arguments!![PARAM_CHARACTER] as CharacterView

//        if (firstTimeCreated(savedInstanceState)) {
//            characterDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_CHARACTER) as CharacterView).id)
//        } else {
//        characterDetailAnimator.scaleUpView(moviePoster)
//        characterDetailAnimator.cancelTransition(moviePoster)
        val character: CharacterView = arguments!![PARAM_CHARACTER] as CharacterView
        moviePoster.loadFromUrl(character.thumbnail.path+"."+character.thumbnail.extension)
        renderMovieDetails(CharacterDetailsView(character.id, character.name, character.thumbnail.path+"."+character.thumbnail.extension, character.description))

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

        comicsAdapter.clickListener = { position ->
            showImageDialog(comicsAdapter, position)
            }


        storiesAdapter.clickListener = { position ->
            showImageDialog(storiesAdapter, position)
        }


        seriesAdapter.clickListener = { position ->
            showImageDialog(seriesAdapter, position)
        }


        eventsAdapter.clickListener = { position ->
            showImageDialog(eventsAdapter, position)
        }


    }




    override fun onBackPressed() {
        characterDetailAnimator.fadeInvisible(scrollView, movieDetails)
//        if (moviePlay.isVisible())
//            characterDetailAnimator.scaleDownView(moviePlay)
//        else
//            characterDetailAnimator.cancelTransition(moviePoster)
    }

    private fun loadComics() {
        showProgress()
        characterDetailsViewModel.loadComics(character.id, "comics")
    }

    private fun loadStories() {
        showProgress()
        characterDetailsViewModel.loadStories(character.id, "stories")
    }

    private fun loadSeries() {
        showProgress()
        characterDetailsViewModel.loadSeries(character.id, "series")
    }

    private fun loadEvents() {
        showProgress()
        characterDetailsViewModel.loadEvents(character.id, "events")
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

    private fun renderMovieDetails(character: CharacterDetailsView?) {
        character?.let {
            with(character) {
                activity?.let {
                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
                    it.toolbar.title = name
                }
                nameTextView.text = name
                descTextView.text = desciption
//                moviePlay.setOnClickListener { characterDetailsViewModel.playMovie(trailer) }
            }
        }
        characterDetailAnimator.fadeVisible(scrollView, movieDetails)

        characterDetailAnimator.scaleUpView(moviePoster)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> { notify(R.string.failure_network_connection); close() }
//            is ServerError -> { notify(R.string.failure_server_error); close() }
//            is NonExistentMovie -> { notify(R.string.failure_character_non_existent); close() }
//            is Failure.ResourceError -> { notify(R.string.failure_resource_non_existent); close() }
        }
    }


    private fun showImageDialog(resourceAdapter: ResourceAdapter, position: Int) {
        val dialog = Dialog(activity!!, R.style.FullScreenDialogStyle)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.setLayout(width, height)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.custom_layout)
        val llm = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        dialog.resourceList.layoutManager = llm
        dialog.resourceList.adapter = dialogAdapter
        dialogAdapter.collection = resourceAdapter.collection
        dialogAdapter.notifyDataSetChanged()

        llm.scrollToPosition(position)


        dialog.closeButton.setOnClickListener{
            dialog.dismiss()
        }



        dialog .show()

    }
}
