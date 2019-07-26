package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.core.interactor.UseCase
import javax.inject.Inject

class GetCharacter
@Inject constructor(private val charactersRepository: CharactersRepository) : UseCase<List<CharacterEntity>, GetCharacter.Params>() {

    override suspend fun run(params: Params) = charactersRepository.characters(params.offset)

    data class Params(val offset: Int)
}