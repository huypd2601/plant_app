package com.example.app2.liked_item

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app2.articles.Articles
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "LikedItemVM"
private const val TAG1 = "LikedItemVM1"

//private val userId : String
class LikedItemVM (): ViewModel() {
    private var dataSet : ArrayList<LikedItem> = arrayListOf()
    private var _listOfLikedItem: MutableLiveData<List<LikedItem>> = MutableLiveData()
    var likedItemType : String ?= null

    val listOfLikedItem: LiveData<List<LikedItem>>
        get() = _listOfLikedItem

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadData(opt: Boolean , userId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            // val dataSet = DataStore.getDataSet()
            dataSet.clear()
            likedItemType = if (opt) {
                "liked_articles"
            } else {
                "liked_plants"
            }
            // firebase
            val db = Firebase.firestore

            db.collection("profile")
                .document("profile")
                .collection("profile")
                .document(userId)
                .collection(likedItemType.toString())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                            println(document.id)
                            val likedItem : LikedItem = document.toObject(LikedItem::class.java)
                            println(likedItem)
                            dataSet.add(likedItem)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
            Log.d(TAG1, opt.toString())
            delay(3000)
            println(dataSet.size)
            _isLoading.postValue(false)
            _listOfLikedItem.postValue(dataSet)

        }
    }
    fun handleItemWhenClicked(view: View ,item: LikedItem) {

    }
}