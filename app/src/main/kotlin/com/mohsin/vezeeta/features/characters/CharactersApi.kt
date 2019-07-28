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

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharactersApi {
    companion object {
        private const val PARAM_LIMIT = "20"
        private const val TS = "1"
        private const val HASH = "0d09defc15e7c90c0f281c39657268b0"
        private const val API_KEY = "71ae45daea9d85be645550b5b00c9354"
        private const val PARAM_MOVIE_ID = "movieId"
        private const val PARAM_CHARACTER_ID = "charId"
        private const val PARAM_RESOURCE = "resourceType"
        private const val MOVIES = "movies.json"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
        private const val CHARACTERS = "v1/public/characters?ts=$TS&hash=$HASH&apikey=$API_KEY&limit=$PARAM_LIMIT"
        private const val RESOURCES = "v1/public/characters/{$PARAM_CHARACTER_ID}/{$PARAM_RESOURCE}?ts=$TS&hash=$HASH&apikey=$API_KEY&limit=$PARAM_LIMIT"
    }//http://gateway.marvel.com/v1/public/characters/1009144/comics

    @GET(MOVIES) fun movies(): Call<List<MovieEntity>>
    @GET(CHARACTERS) fun characters(@Query("offset") offSet: Int): Call<CharactersResponse>
    @GET(CHARACTERS) fun characterSearch(@Query("offset") offSet: Int, @Query("nameStartsWith") nameStartsWith: String): Call<CharactersResponse>
    @GET(RESOURCES) fun characterResource(@Path(PARAM_CHARACTER_ID) characterId: Int, @Path(PARAM_RESOURCE) resource: String): Call<ResourceResponse>
}
