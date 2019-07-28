package com.mohsin.vezeeta.features.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity (@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String = "", val description: String = "", val thumbnail: Thumbnail = Thumbnail(),
                            val comics: Resource = Resource(), val series: Resource = Resource(), val stories: Resource = Resource(), val events: Resource = Resource(),
                            val urls: List<RelatedLink> = emptyList())