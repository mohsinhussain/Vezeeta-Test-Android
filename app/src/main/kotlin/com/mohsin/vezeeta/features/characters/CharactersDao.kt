package com.mohsin.vezeeta.features.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CharactersDao {

    @Query("SELECT * FROM characterentity LIMIT 20 OFFSET :offset")
    fun getAllCharacters(offset: Int): List<CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE name LIKE :nameStartsWith LIMIT 20 OFFSET :offset")
    fun getCharactersSearched(offset: Int, nameStartsWith: String): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacter(character: CharacterEntity): Long


}