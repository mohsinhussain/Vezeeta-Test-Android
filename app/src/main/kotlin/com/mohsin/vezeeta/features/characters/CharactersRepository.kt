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

import com.mohsin.vezeeta.core.exception.Failure
import com.mohsin.vezeeta.core.exception.Failure.NetworkConnection
import com.mohsin.vezeeta.core.exception.Failure.ServerError
import com.mohsin.vezeeta.core.functional.Either
import com.mohsin.vezeeta.core.functional.Either.Left
import com.mohsin.vezeeta.core.functional.Either.Right
import com.mohsin.vezeeta.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface CharactersRepository {
    fun movies(): Either<Failure, List<Movie>>
    fun characters(limit: Int): Either<Failure, List<CharacterEntity>>
    fun characterReources(characterId: Int, resource: String): Either<Failure, List<ResourceEntity>>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private val offlineService: CharactersOfflineService,
                        private val onlineService: CharactersService) : CharactersRepository {
        override fun characters(offset: Int): Either<Failure, List<CharacterEntity>> {
            return when (networkHandler.isConnected) {
                true -> request(
                        onlineService.characters(offset), {
                    it.data.results.map {
                        saveCharacterToDB(it)
                    }
                },
                        CharactersResponse())
                false, null -> {
                    val list: List<CharacterEntity> = offlineService.characters(offset)
                    if(list.size>0){
                        println(list.size.toString() + " characters loaded")
                        return Right(list)

                    }
                    else{
                        return Left(NetworkConnection)
                    }

                }//Left(NetworkConnection)
            }
        }

        override fun movies(): Either<Failure, List<Movie>> {
            return when (networkHandler.isConnected) {
                true -> request(onlineService.movies(), { it.map { it.toMovie() } }, emptyList())
                false, null -> Left(NetworkConnection)
            }
        }

        override fun characterReources(characterId: Int, resource: String): Either<Failure, List<ResourceEntity>> {
            return when (networkHandler.isConnected) {
                true -> request(
                        onlineService.characterResource(characterId, resource), {
                    it.data.results.map {
                        saveCharacterResourceToDB(it, characterId, resource)
                    }
                },
                        ResourceResponse())
                false, null -> {
                    val list: List<ResourceEntity> = offlineService.resources(characterId, resource)
                    if(list.size>0){
                        println(list.size.toString() + " resources loaded")
                        return Right(list)

                    }
                    else{
                        return Left(Failure.ResourceError)
                    }

                }//Left(NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                Left(ServerError)
            }
        }

        fun saveCharacterResourceToDB(resource: ResourceEntity, characterId: Int, resourceType: String): ResourceEntity{
            resource.characterid = characterId
            resource.resourceType = resourceType
            val id = offlineService.addResource(resource)
            println("Resource added with id: "+ id)
            return resource
        }

        fun saveCharacterToDB(character: CharacterEntity): CharacterEntity{
            val id = offlineService.addCharacter(character)
            println("Character added with id: "+ id)
            return character
        }
    }
}
