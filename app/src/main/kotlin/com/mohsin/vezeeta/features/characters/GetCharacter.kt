package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.core.interactor.UseCase
import javax.inject.Inject

class GetCharacter
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<CharacterEntity>, UseCase.None>() {

    override suspend fun run(params: None) = moviesRepository.characters()
}