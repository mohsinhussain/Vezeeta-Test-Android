
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.features.characters.GetCharacterResource.Params
import com.mohsin.vezeeta.core.interactor.UseCase
import javax.inject.Inject

class GetCharacterResource
@Inject constructor(private val charactersRepository: CharactersRepository) : UseCase<List<ResourceEntity>, Params>() {

    override suspend fun run(params: Params) = charactersRepository.characterReources(params.id, params.resource)

    data class Params(val id: Int, val resource: String)
}
