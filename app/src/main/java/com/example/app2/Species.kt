package com.example.app2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Species (
    var name:String ?= null
) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!
    ) {
    }

//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        dest.writeString(name)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//    companion object CREATOR : Parcelable.Creator<Species> {
//        override fun createFromParcel(parcel: Parcel): Species {
//            return Species(parcel)
//        }
//        override fun newArray(size: Int): Array<Species?> {
//            return arrayOfNulls(size)
//        }
//    }

}


