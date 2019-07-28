package com.mohsin.vezeeta.features.characters

import android.os.Parcel
import com.mohsin.vezeeta.core.platform.KParcelable
import com.mohsin.vezeeta.core.platform.parcelableCreator

data class  RelatedLink (val type: String, val url: String): KParcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!)

    companion object {
        @JvmField val CREATOR = parcelableCreator(::RelatedLink)
    }


    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(type)
            writeString(url)
        }
    }

    override fun describeContents(): Int {
        return 0
    }


}
