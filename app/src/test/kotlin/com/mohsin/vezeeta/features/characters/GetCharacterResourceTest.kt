
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.UnitTest
import com.mohsin.vezeeta.core.functional.Either.Right
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCharacterResourceTest : UnitTest() {

    private val CHAR_ID = 1
    private val RESOURCE_TYPE = "comics"

    private lateinit var getCharacterResource: GetCharacterResource

    @Mock private lateinit var charactersRepository: CharactersRepository

    @Before fun setUp() {
        getCharacterResource = GetCharacterResource(charactersRepository)
        given { charactersRepository.characterReources(CHAR_ID, RESOURCE_TYPE) }.willReturn(Right(listOf(ResourceEntity())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getCharacterResource.run(GetCharacterResource.Params(CHAR_ID, RESOURCE_TYPE)) }

        verify(charactersRepository).characterReources(CHAR_ID, RESOURCE_TYPE)
        verifyNoMoreInteractions(charactersRepository)
    }
}
