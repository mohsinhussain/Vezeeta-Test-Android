
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
    fun characters(offSet: Int, nameStartsWith: String): Either<Failure, List<CharacterEntity>>
    fun characterReources(characterId: Int, resource: String): Either<Failure, List<ResourceEntity>>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private val offlineService: CharactersOfflineService,
                        private val onlineService: CharactersService) : CharactersRepository {
        override fun characters(offset: Int, nameStartsWith: String): Either<Failure, List<CharacterEntity>> {
            return when (networkHandler.isConnected) {
                true -> request(
                        if(nameStartsWith.contentEquals("")){
                            onlineService.characters(offset)
                        }
                                else{
                            onlineService.characterSearch(offset, nameStartsWith)
                        }
                        , {
                    it.data.results.map {
                        saveCharacterToDB(it)
                    }
                },
                        CharactersResponse())
                false, null -> {
                    val list: List<CharacterEntity> = if(nameStartsWith.contentEquals("")){
                        offlineService.characters(offset)
                    } else{
                        offlineService.characterSearch(offset, nameStartsWith)
                    }

                    return if(list.size>0){
                        println(list.size.toString() + " characters loaded")
                        Right(list)

                    } else{
                        Left(NetworkConnection)
                    }

                }
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

                }
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

        private fun saveCharacterResourceToDB(resource: ResourceEntity, characterId: Int, resourceType: String): ResourceEntity{
            resource.characterid = characterId
            resource.resourceType = resourceType
            val id = offlineService.addResource(resource)
            println("Resource added with id: $id")
            return resource
        }

        private fun saveCharacterToDB(character: CharacterEntity): CharacterEntity{
            val id = offlineService.addCharacter(character)
            println("Character added with id: $id")
            return character
        }
    }
}
