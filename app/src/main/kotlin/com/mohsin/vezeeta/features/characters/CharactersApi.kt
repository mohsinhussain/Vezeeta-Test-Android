
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
        private const val PARAM_CHARACTER_ID = "charId"
        private const val PARAM_RESOURCE = "resourceType"
        private const val CHARACTERS = "v1/public/characters?ts=$TS&hash=$HASH&apikey=$API_KEY&limit=$PARAM_LIMIT"
        private const val RESOURCES = "v1/public/characters/{$PARAM_CHARACTER_ID}/{$PARAM_RESOURCE}?ts=$TS&hash=$HASH&apikey=$API_KEY&limit=$PARAM_LIMIT"
    }


    @GET(CHARACTERS) fun characters(@Query("offset") offSet: Int): Call<CharactersResponse>
    @GET(CHARACTERS) fun characterSearch(@Query("offset") offSet: Int, @Query("nameStartsWith") nameStartsWith: String): Call<CharactersResponse>
    @GET(RESOURCES) fun characterResource(@Path(PARAM_CHARACTER_ID) characterId: Int, @Path(PARAM_RESOURCE) resource: String): Call<ResourceResponse>
}
