
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CharacterDetailsViewModelTest : AndroidTest() {

    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel

    @Mock private lateinit var getCharacterResource: GetCharacterResource
    @Mock private lateinit var playMovie: PlayMovie

    @Before
    fun setUp() {
        characterDetailsViewModel = CharacterDetailsViewModel(getCharacterResource, playMovie)
    }

    @Test fun `loading movie details should update live data`() {
        val movieDetails = MovieDetails(0, "IronMan", "poster", "summary",
                "cast", "director", 2018, "trailer")
        given { runBlocking { getCharacterResource.run(eq(any())) } }.willReturn(Right(movieDetails))

        characterDetailsViewModel.movieDetails.observeForever {
            with(it!!) {
                id shouldEqualTo 0
                title shouldEqualTo "IronMan"
                poster shouldEqualTo "poster"
                summary shouldEqualTo "summary"
                cast shouldEqualTo "cast"
                director shouldEqualTo "director"
                year shouldEqualTo 2018
                trailer shouldEqualTo "trailer"
            }
        }

        runBlocking { characterDetailsViewModel.loadMovieDetails(0) }
    }
}