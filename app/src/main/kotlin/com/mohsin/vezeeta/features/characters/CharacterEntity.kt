package com.mohsin.vezeeta.features.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity (@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val description: String, val thumbnail: Thumbnail,
                            val comics: Resource, val series: Resource, val stories: Resource, val events: Resource, val urls: List<RelatedLink>)