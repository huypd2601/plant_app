package com.example.app2.liked_item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LikedItem (
    var desc: String ?= null,
    var image: String ?= null
) : Parcelable
