package com.mohsin.vezeeta.core.di.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohsin.vezeeta.features.characters.Thumbnail


class ThumbnailConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToMainObject(data: String?): Thumbnail? {
        if (data == null) {
            return null
        }

        val typeOfObject = object : TypeToken<Thumbnail>() {

        }.getType()

        return gson.fromJson(data, typeOfObject)
    }

    @TypeConverter
    fun mainObjectToString(someObjects: Thumbnail): String {
        return gson.toJson(someObjects)
    }
}