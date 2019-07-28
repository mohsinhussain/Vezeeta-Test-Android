
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CharactersViewModelTest : AndroidTest() {

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock private lateinit var getCharacter: GetCharacter

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharacter)
    }

    @Test fun `loading characters should update live data`() {
        val characterList = listOf(CharacterEntity(0, "IronMan"), CharacterEntity(1, "Superman"))
        given { runBlocking { getCharacter.run(eq(any())) } }.willReturn(Right(characterList))

        charactersViewModel.characters.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].name shouldEqualTo "IronMan"
            it[1].id shouldEqualTo 1
            it[1].name shouldEqualTo "Superman"
        }

        runBlocking { charactersViewModel.loadCharacters(0, "") }
    }
}