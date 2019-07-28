
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.UnitTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCharacterResourceTest : UnitTest() {

    private val MOVIE_ID = 1

    private lateinit var getCharacterResource: GetCharacterResource

    @Mock private lateinit var charactersRepository: CharactersRepository

    @Before fun setUp() {
        getCharacterResource = GetCharacterResource(charactersRepository)
        given { charactersRepository.movieDetails(MOVIE_ID) }.willReturn(Right(MovieDetails.empty()))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getCharacterResource.run(GetCharacterResource.Params(MOVIE_ID)) }

        verify(charactersRepository).movieDetails(MOVIE_ID)
        verifyNoMoreInteractions(charactersRepository)
    }
}
