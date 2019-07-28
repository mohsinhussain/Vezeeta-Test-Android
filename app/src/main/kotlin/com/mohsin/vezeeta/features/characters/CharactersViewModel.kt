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
import com.mohsin.vezeeta.core.platform.BaseViewModel
import javax.inject.Inject

class CharactersViewModel
@Inject constructor(private val getCharacters: GetCharacter, private val getCharacterResource: GetCharacterResource) : BaseViewModel() {

    var movies: MutableLiveData<List<CharacterView>> = MutableLiveData()

//    fun loadMovies() = getMovies(None()) { it.either(::handleFailure, ::handleMovieList) }

//    private fun handleMovieList(movies: List<Movie>) {
//        this.movies.value = movies.map { CharacterView(it.id, it.poster, it.poster) }
//    }


    fun loadCharacters(offset: Int, nameStartsWith: String) = getCharacters(GetCharacter.Params(offset, nameStartsWith)) {
        it.either(::handleFailure, ::handleCharactersList)
    }

    private fun handleCharactersList(characters: List<CharacterEntity>) {
        this.movies.value = characters.map { CharacterView(it.id, it.name, it.description, it.thumbnail, it.comics, it.series, it.stories,
                it.events, it.urls) }
    }






}