
package com.mohsin.vezeeta.features.characters

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersOfflineService
@Inject constructor(private val charactersDao: CharactersDao, private val resourceDao: ResourceDao) {

    fun characters(offset: Int) = charactersDao.getAllCharacters(offset)
    fun characterSearch(offset: Int, nameStartsWith: String) = charactersDao.getCharactersSearched(offset, "$nameStartsWith%")
    fun addCharacter(character: CharacterEntity) = charactersDao.addCharacter(character).toInt()

    fun resources(characterId: Int, resourceType: String) = resourceDao.getAllResources(characterId, resourceType)
    fun addResource(resource: ResourceEntity) = resourceDao.addResource(resource).toInt()
}
