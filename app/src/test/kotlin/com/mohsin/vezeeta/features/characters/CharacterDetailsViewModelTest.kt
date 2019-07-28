
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.AndroidTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.internal.matchers.Null

class CharacterDetailsViewModelTest : AndroidTest() {

    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel

    @Mock private lateinit var getCharacterResource: GetCharacterResource

    @Before
    fun setUp() {
        characterDetailsViewModel = CharacterDetailsViewModel(getCharacterResource)
    }

    @Test fun `loading comics should update live data`() {
        val comics = listOf(ResourceEntity(0, 0, "comic1", "comic", Thumbnail("path1", ".jpg")),
                ResourceEntity(1, 1, "comic2", "comics", Thumbnail("path2", ".png")))
        given { runBlocking { getCharacterResource.run(eq(any())) } }.willReturn(Right(comics))

        characterDetailsViewModel.comics.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].characterid shouldEqualTo 0
            it[0].title shouldEqualTo "comic1"
            it[0].resourceType shouldEqualTo "comics"
            it[0].thumbnail!!.path shouldEqualTo "path1"
            it[0].thumbnail!!.extension shouldEqualTo ".jpg"

            it[1].id shouldEqualTo 1
            it[1].characterid shouldEqualTo 1
            it[1].title shouldEqualTo "comic2"
            it[1].resourceType shouldEqualTo "comics"
            it[1].thumbnail!!.path shouldEqualTo "path2"
            it[1].thumbnail!!.extension shouldEqualTo ".png"
        }

        runBlocking { characterDetailsViewModel.loadComics(0, "comics") }
    }

    @Test fun `loading series should update live data`() {
        val series = listOf(ResourceEntity(0, 0, "series1", "series", Thumbnail("path1", ".jpg")),
                ResourceEntity(1, 1, "series2", "series", null))
        given { runBlocking { getCharacterResource.run(eq(any())) } }.willReturn(Right(series))

        characterDetailsViewModel.series.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].characterid shouldEqualTo 0
            it[0].title shouldEqualTo "series1"
            it[0].resourceType shouldEqualTo "series"
            it[0].thumbnail!!.path shouldEqualTo "path1"
            it[0].thumbnail!!.extension shouldEqualTo ".jpg"

            it[1].id shouldEqualTo 1
            it[1].characterid shouldEqualTo 1
            it[1].title shouldEqualTo "series2"
            it[1].resourceType shouldEqualTo "series"
            it[1].thumbnail shouldBe null
        }

        runBlocking { characterDetailsViewModel.loadComics(0, "comics") }
    }

}