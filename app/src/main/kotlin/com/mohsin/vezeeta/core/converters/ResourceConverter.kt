package com.mohsin.vezeeta.core.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohsin.vezeeta.features.characters.Resource
import com.mohsin.vezeeta.features.characters.Thumbnail


class ResourceConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToMainObject(data: String?): Resource? {
        if (data == null) {
            return null
        }

        val typeOfObject = object : TypeToken<Resource>() {

        }.type

        return gson.fromJson(data, typeOfObject)
    }

    @TypeConverter
    fun mainObjectToString(someObjects: Resource): String {
        return gson.toJson(someObjects)
    }
}