package com.example.app2.plant

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant (
    var desc: String ?= null,
    var family: String ?= null ,
    var image:String ?= null,
    var kingdom : String ?= null ,
    var like: Int ?= null,
    var isLiked : Boolean ?= null,
    var name:String ?= null
) : Parcelable
{
//    constructor(parcel: Parcel) : this(
////        parcel.readString()!!,
////        parcel.readString()!!,
////        parcel.readString()!!,
////        parcel.readString()!!,
////        parcel.readInt(),
////        parcel.readString()!!
//    ) {
//    }
////    override fun writeToParcel(parcel: Parcel, flags: Int) {
////        parcel.writeString(desc)
////        parcel.writeString(family)
////        parcel.writeString(image)
////        parcel.writeString(kingdom)
////        parcel.writeInt(like!!)
////        parcel.writeString(name)
////    }
//    override fun describeContents(): Int {
//        return 0
//    }
////    companion object CREATOR : Parcelable.Creator<Plant> {
////        override fun createFromParcel(parcel: Parcel): Plant {
////            return Plant(parcel)
////        }
////        override fun newArray(size: Int): Array<Plant?> {
////            return arrayOfNulls(size)
////        }
////    }

}


