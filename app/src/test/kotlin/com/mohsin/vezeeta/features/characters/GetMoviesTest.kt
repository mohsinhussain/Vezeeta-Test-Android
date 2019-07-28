
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.UnitTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.mohsin.vezeeta.core.interactor.UseCase
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

    private lateinit var getMovies: GetMovies

    @Mock private lateinit var charactersRepository: CharactersRepository

    @Before fun setUp() {
        getMovies = GetMovies(charactersRepository)
        given { charactersRepository.movies() }.willReturn(Right(listOf(Movie.empty())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getMovies.run(UseCase.None()) }

        verify(charactersRepository).movies()
        verifyNoMoreInteractions(charactersRepository)
    }
}
