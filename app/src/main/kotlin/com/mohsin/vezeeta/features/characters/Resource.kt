package com.mohsin.vezeeta.features.characters

import android.os.Parcel
import android.os.Parcelable
import com.mohsin.vezeeta.core.extension.empty
import com.mohsin.vezeeta.core.platform.KParcelable

data class Resource (val collectionURI: String) : KParcelable {

    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

//    companion object {
//        fun empty() = Resource(String.empty())
//    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest){
            writeString(collectionURI)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Resource> {
        override fun createFromParcel(parcel: Parcel): Resource {
            return Resource(parcel)
        }

        override fun newArray(size: Int): Array<Resource?> {
            return arrayOfNulls(size)
        }
    }

}
