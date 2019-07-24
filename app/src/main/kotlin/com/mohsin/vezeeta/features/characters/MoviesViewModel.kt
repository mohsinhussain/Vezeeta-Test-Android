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

import android.arch.lifecycle.MutableLiveData
import com.mohsin.vezeeta.core.interactor.UseCase.None
import com.mohsin.vezeeta.core.platform.BaseViewModel
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies, private val getCharacters: GetCharacter) : BaseViewModel() {

    var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

    fun loadMovies() = getMovies(None()) { it.either(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        this.movies.value = movies.map { MovieView(it.id, it.poster) }
    }


    fun loadCharacters() = getCharacters(None()) {
        it.either(::handleFailure, ::handleCharactersList)
    }

    private fun handleCharactersList(characters: List<CharacterEntity>) {
        this.movies.value = characters.map { MovieView(it.id, "") }
    }






}