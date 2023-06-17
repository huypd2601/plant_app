package com.example.app2.liked_item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class LikedItemViewModelFactory(private val userId: String, private var opt : Boolean) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        if (modelClass.isAssignableFrom(LikedItemVM::class.java)) {
//            Log.d("profileFragment*", opt.toString())
//            return LikedItemVM() as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }

}
