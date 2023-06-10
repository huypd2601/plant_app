package com.example.app2.articles


import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.app2.plant.Plant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "ArticlesVM"
private const val TAG1 = "AriticlesVM1"

class ArticlesVM : ViewModel() {
    private val dataSet : ArrayList<Articles> = arrayListOf()
    private var _listOfArticles: MutableLiveData<List<Articles>> = MutableLiveData()
    val listOfArticles: LiveData<List<Articles>>
        get() = _listOfArticles

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadData() {
        _isLoading.postValue(true)

        viewModelScope.launch {

            // val dataSet = DataStore.getDataSet()
            dataSet.clear()
            // firebase
            val db = Firebase.firestore

            db.collection("articles")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val articles : Articles = document.toObject(Articles::class.java)
                    println(articles)
                    dataSet.add(articles)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

            delay(3000)
            println(dataSet.size)
            _isLoading.postValue(false)
            _listOfArticles.postValue(dataSet)
        }
    }

    fun filter(searchText: String) {
        val dataSetInner: ArrayList<Articles> = arrayListOf()
        if (searchText.isNotEmpty()) {
            dataSet.forEach {
                if (it.articleTitle?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {
                    dataSetInner.add(it)
                }
            }
            _listOfArticles.value = dataSetInner
        }
        else
            _listOfArticles.value = dataSet
    }

    fun handleItemWhenClicked(view: View ,item: Articles) {
        val action = ArticlesFragmentDirections.actionArticlesFragmentToDetailArticlesFragment(item)
        view.findNavController().navigate(action)

    }

}

