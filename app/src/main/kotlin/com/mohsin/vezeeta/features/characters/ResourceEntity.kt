package com.mohsin.vezeeta.features.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResourceEntity (@PrimaryKey(autoGenerate = true) var id: Int, var characterid: Int, var title: String, var resourceType: String, var thumbnail: Thumbnail?) {

}
