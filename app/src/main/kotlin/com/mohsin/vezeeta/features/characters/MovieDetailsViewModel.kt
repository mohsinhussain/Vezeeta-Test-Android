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

import androidx.lifecycle.MutableLiveData
import com.mohsin.vezeeta.features.characters.GetCharacterResource.Params
import com.mohsin.vezeeta.core.platform.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getCharacterResource: GetCharacterResource,
                    private val playMovie: PlayMovie) : BaseViewModel() {

    var comics: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var series: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var stories: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var events: MutableLiveData<List<ResourceEntity>> = MutableLiveData()

//    fun loadMovieDetails(movieId: Int) =
//            getCharacterResource(Params(movieId)) { it.either(::handleFailure, ::handleMovieDetails) }

    fun loadComics(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleComicsList) }

    fun loadSeries(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleSeriesList) }

    fun loadStories(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleStoriesList) }

    fun loadEvents(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleEventsList) }

    fun playMovie(url: String) = playMovie(PlayMovie.Params(url))

//    private fun handleMovieDetails(resources: MovieDetails) {
//        this.movieDetails.value = MovieDetailsView(movie.id, movie.name, movie.poster,
//                movie.description, movie.comics, movie.series, movie.stories, movie.links)
//    }


    private fun handleComicsList(resources: List<ResourceEntity>) {
        this.comics.value = resources.map { ResourceEntity(it.id, it.characterid, it.title, it.resourceType, it.thumbnail) }
    }

    private fun handleSeriesList(resources: List<ResourceEntity>) {
        this.series.value = resources.map { ResourceEntity(it.id, it.characterid, it.title, it.resourceType, it.thumbnail) }
    }

    private fun handleStoriesList(resources: List<ResourceEntity>) {
        this.stories.value = resources.map { ResourceEntity(it.id, it.characterid, it.title, it.resourceType, it.thumbnail) }
    }

    private fun handleEventsList(resources: List<ResourceEntity>) {
        this.events.value = resources.map { ResourceEntity(it.id, it.characterid, it.title, it.resourceType, it.thumbnail) }
    }
}