
package com.mohsin.vezeeta.features.characters

import com.mohsin.vezeeta.UnitTest
import com.mohsin.vezeeta.features.characters.CharactersRepository.Network
import com.mohsin.vezeeta.core.exception.Failure.NetworkConnection
import com.mohsin.vezeeta.core.exception.Failure.ServerError
import com.mohsin.vezeeta.core.extension.empty
import com.mohsin.vezeeta.core.functional.Either
import com.mohsin.vezeeta.core.functional.Either.Right
import com.mohsin.vezeeta.core.platform.NetworkHandler
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CharactersRepositoryTest : UnitTest() {

    private lateinit var networkRepository: CharactersRepository.Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: CharactersService
    @Mock private lateinit var offlineService: CharactersOfflineService

    @Mock private lateinit var charactersCall: Call<CharactersResponse>
    @Mock private lateinit var charactersResponse: Response<CharactersResponse>

    @Before fun setUp() {
        networkRepository = Network(networkHandler, offlineService, service)
    }

    @Test fun `should return empty list by default`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { charactersResponse.body() }.willReturn(null)
        given { charactersResponse.isSuccessful }.willReturn(true)
        given { charactersCall.execute() }.willReturn(charactersResponse)
        given { service.characters(0) }.willReturn(charactersCall)

        val characters = networkRepository.characters(0, "")

        characters shouldEqual Right(emptyList<CharacterEntity>())
        verify(service).characters(0)
    }



    @Test fun `should get character list from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { charactersResponse.body() }.willReturn(CharactersResponse(CharacterData(listOf(CharacterEntity(1, "superMan")))))
        given { charactersResponse.isSuccessful }.willReturn(true)
        given { charactersCall.execute() }.willReturn(charactersResponse)
        given { service.characters(0) }.willReturn(charactersCall)

        val characters = networkRepository.characters(0, "")

        characters shouldEqual Right(listOf(CharacterEntity(1, "superMan")))
        verify(service).characters(0)
    }

    @Test fun `characters service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val characters = networkRepository.characters(0, "")

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `characters service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val characters = networkRepository.characters(0, "")

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `characters service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val characters = networkRepository.characters(0, "")

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `characters request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val characters = networkRepository.characters(0, "")

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

}