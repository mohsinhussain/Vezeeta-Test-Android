package com.mohsin.vezeeta.features.characters


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ResourceDao {

    @Query("SELECT * FROM resourceentity WHERE characterid = :characterId AND resourceType = :resourceType LIMIT 20")
    fun getAllResources(characterId: Int, resourceType: String): List<ResourceEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResource(resource: ResourceEntity): Long


}