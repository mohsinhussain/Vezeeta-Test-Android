package com.mohsin.vezeeta.features.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity (@PrimaryKey(autoGenerate = true) val id: Int, val name: String, val description: String, val modified: String, val thumbnail: Thumbnail, val resourceURI: String) {

}
