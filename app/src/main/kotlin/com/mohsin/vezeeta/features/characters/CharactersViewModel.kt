
package com.mohsin.vezeeta.features.characters

import androidx.lifecycle.MutableLiveData
import com.mohsin.vezeeta.core.platform.BaseViewModel
import javax.inject.Inject

class CharactersViewModel
@Inject constructor(private val getCharacters: GetCharacter) : BaseViewModel() {

    var movies: MutableLiveData<List<CharacterView>> = MutableLiveData()

    fun loadCharacters(offset: Int, nameStartsWith: String) = getCharacters(GetCharacter.Params(offset, nameStartsWith)) {
        it.either(::handleFailure, ::handleCharactersList)
    }

    private fun handleCharactersList(characters: List<CharacterEntity>) {
        this.movies.value = characters.map { CharacterView(it.id, it.name, it.description, it.thumbnail, it.comics, it.series, it.stories,
                it.events, it.urls) }
    }






}