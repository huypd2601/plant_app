package com.example.app2.articles

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Articles (
    var articleImage: String ?= null,
    var articleTitle: String ?= null,
    var authorAva:String ?= null,
    var authorName : String ?= null,
    var authorDesc : String ?= null,
    var isLiked : Boolean ?= null,
    var articleId: String ?= null

) : Parcelable
{
//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!
//    ) {
//    }

}