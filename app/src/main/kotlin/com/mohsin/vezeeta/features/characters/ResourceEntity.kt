package com.mohsin.vezeeta.features.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResourceEntity (@PrimaryKey(autoGenerate = true) var id: Int = 0, var characterid: Int = 0, var title: String = "", var resourceType: String = "",
                           var thumbnail: Thumbnail? = Thumbnail())
