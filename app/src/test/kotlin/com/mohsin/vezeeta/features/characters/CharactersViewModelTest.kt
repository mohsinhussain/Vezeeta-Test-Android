
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CharactersViewModelTest : AndroidTest() {

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock private lateinit var getMovies: GetMovies

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getMovies)
    }

    @Test fun `loading movies should update live data`() {
        val moviesList = listOf(Movie(0, "IronMan"), Movie(1, "Batman"))
        given { runBlocking { getMovies.run(eq(any())) } }.willReturn(Right(moviesList))

        charactersViewModel.movies.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].poster shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].poster shouldEqualTo "Batman"
        }

        runBlocking { charactersViewModel.loadMovies() }
    }
}