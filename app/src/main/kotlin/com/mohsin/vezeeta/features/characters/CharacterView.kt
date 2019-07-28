/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
