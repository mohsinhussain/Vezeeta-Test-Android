package com.mohsin.vezeeta.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohsin.vezeeta.core.converters.RelatedLinksConverter
import com.mohsin.vezeeta.core.converters.ResourceConverter
import com.mohsin.vezeeta.core.converters.ThumbnailConverter
import com.mohsin.vezeeta.features.characters.CharacterEntity
import com.mohsin.vezeeta.features.characters.CharactersDao
import com.mohsin.vezeeta.features.characters.ResourceDao
import com.mohsin.vezeeta.features.characters.ResourceEntity


@Database(entities = arrayOf(CharacterEntity::class, ResourceEntity::class), version = 6, exportSchema = false)
@TypeConverters(ThumbnailConverter::class, ResourceConverter::class, RelatedLinksConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun resourceDao(): ResourceDao

}