package com.mohsin.vezeeta.core.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohsin.vezeeta.features.characters.RelatedLink
import java.util.*

class RelatedLinksConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToWeatherObjectList(data: String?): List<RelatedLink> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<RelatedLink>>() {

        }.getType()

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun weatherObjectListToString(someObjects: List<RelatedLink>): String {
        return gson.toJson(someObjects)
    }
}