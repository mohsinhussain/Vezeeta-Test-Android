
package com.mohsin.vezeeta.features.characters

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersService
@Inject constructor(retrofit: Retrofit) : CharactersApi {
    private val charactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override fun characters(offSet: Int) = charactersApi.characters(offSet)
    override fun characterSearch(offSet: Int, nameStartsWith: String) = charactersApi.characterSearch(offSet, nameStartsWith)
    override fun characterResource(characterId: Int, resource: String) = charactersApi.characterResource(characterId, resource)
}
