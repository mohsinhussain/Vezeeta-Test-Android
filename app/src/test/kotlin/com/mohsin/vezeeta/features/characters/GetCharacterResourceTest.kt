/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
