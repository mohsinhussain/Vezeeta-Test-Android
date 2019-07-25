package com.mohsin.vezeeta.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohsin.vezeeta.core.converters.ThumbnailConverter
import com.mohsin.vezeeta.features.characters.CharacterEntity
import com.mohsin.vezeeta.features.characters.CharactersDao


@Database(entities = arrayOf(CharacterEntity::class), version = 1, exportSchema = false)
@TypeConverters(ThumbnailConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

}