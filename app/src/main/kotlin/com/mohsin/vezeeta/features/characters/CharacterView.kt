
package com.mohsin.vezeeta.features.characters

import android.os.Build
import android.os.Parcel
import androidx.annotation.RequiresApi
import com.mohsin.vezeeta.core.platform.KParcelable
import com.mohsin.vezeeta.core.platform.parcelableCreator

data class CharacterView(val id: Int, val name: String, val description: String, val thumbnail: Thumbnail,
                         val comics: Resource, val series: Resource, val stories: Resource, val events: Resource, val urls: List<RelatedLink>) : KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(::CharacterView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readTypedObject(Thumbnail.CREATOR), parcel.readTypedObject(Resource.CREATOR),
            parcel.readTypedObject(Resource.CREATOR), parcel.readTypedObject(Resource.CREATOR), parcel.readTypedObject(Resource.CREATOR),
            mutableListOf<RelatedLink>().apply {
                parcel.readTypedList(this, RelatedLink.CREATOR)
            }
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(name)
            writeString(description)
            writeTypedObject(thumbnail, 1)
            writeTypedObject(comics, 1)
            writeTypedObject(series, 1)
            writeTypedObject(stories, 1)
            writeTypedObject(events, 1)
            writeTypedList(urls)
        }
    }
}
