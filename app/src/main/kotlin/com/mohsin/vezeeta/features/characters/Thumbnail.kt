package com.mohsin.vezeeta.features.characters

import android.os.Parcel
import android.os.Parcelable
import com.mohsin.vezeeta.core.extension.empty
import com.mohsin.vezeeta.core.platform.KParcelable
import com.mohsin.vezeeta.core.platform.parcelableCreator
import kotlinx.android.parcel.Parcelize


data class Thumbnail (val path: String, val extension: String): KParcelable {

//    companion object {
//        @JvmField val CREATOR = parcelableCreator(::Thumbnail)
//    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }



    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest){
            writeString(path)
            writeString(extension)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Thumbnail> {
        override fun createFromParcel(parcel: Parcel): Thumbnail {
            return Thumbnail(parcel)
        }

        override fun newArray(size: Int): Array<Thumbnail?> {
            return arrayOfNulls(size)
        }
    }

}
