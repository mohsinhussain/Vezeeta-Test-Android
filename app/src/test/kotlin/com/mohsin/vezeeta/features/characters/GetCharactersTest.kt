
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.UnitTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.mohsin.vezeeta.core.interactor.UseCase
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCharactersTest : UnitTest() {

    private lateinit var getCharacters: GetCharacter

    @Mock private lateinit var charactersRepository: CharactersRepository

    @Before fun setUp() {
        getCharacters = GetCharacter(charactersRepository)
        given { charactersRepository.characters(0, "") }.willReturn(Right(listOf(CharacterEntity())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getCharacters.run(GetCharacter.Params(0, "")) }

        verify(charactersRepository).characters(0, "")
        verifyNoMoreInteractions(charactersRepository)
    }
}
