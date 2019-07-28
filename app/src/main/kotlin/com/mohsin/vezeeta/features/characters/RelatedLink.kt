package com.mohsin.vezeeta.features.characters

import android.os.Parcel
import android.os.Parcelable
import com.mohsin.vezeeta.core.extension.empty
import com.mohsin.vezeeta.core.platform.KParcelable
import com.mohsin.vezeeta.core.platform.parcelableCreator

data class  RelatedLink (val type: String, val url: String): KParcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::RelatedLink)
    }

//    companion object {
//        fun empty() = RelatedLink(String.empty(), String.empty())
//    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(type)
            writeString(url)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

//    companion object CREATOR : Parcelable.Creator<RelatedLink> {
//        override fun createFromParcel(parcel: Parcel): RelatedLink {
//            return RelatedLink(parcel)
//        }
//
//        override fun newArray(size: Int): Array<RelatedLink?> {
//            return arrayOfNulls(size)
//        }
//    }

}
