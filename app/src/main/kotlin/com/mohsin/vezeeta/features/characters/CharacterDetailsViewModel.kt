
package com.mohsin.vezeeta.features.characters

import androidx.lifecycle.MutableLiveData
import com.mohsin.vezeeta.features.characters.GetCharacterResource.Params
import com.mohsin.vezeeta.core.platform.BaseViewModel
import javax.inject.Inject

class CharacterDetailsViewModel
@Inject constructor(private val getCharacterResource: GetCharacterResource) : BaseViewModel() {

    var comics: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var series: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var stories: MutableLiveData<List<ResourceEntity>> = MutableLiveData()
    var events: MutableLiveData<List<ResourceEntity>> = MutableLiveData()

    fun loadComics(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleComicsList) }

    fun loadSeries(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleSeriesList) }

    fun loadStories(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleStoriesList) }

    fun loadEvents(characterId: Int, resource: String) =
            getCharacterResource(Params(characterId, resource)) { it.either(::handleFailure, ::handleEventsList) }


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