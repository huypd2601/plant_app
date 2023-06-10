package com.example.app2.species

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


}


