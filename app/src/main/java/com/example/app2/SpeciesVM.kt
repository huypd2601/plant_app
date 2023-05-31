package com.example.app2


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "SpeciesVM"
private const val TAG1 = "SpeciesVM1"

class SpeciesVM : ViewModel() {
    private val dataSet : ArrayList<Species> = arrayListOf()
    private var _listOfSpecies: MutableLiveData<List<Species>> = MutableLiveData()
    val listOfSpecies: LiveData<List<Species>>
        get() = _listOfSpecies

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

            db.collection("species")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val species = Species(document.id)
                    dataSet.add(species)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

            delay(3000)
            println(dataSet.size)
            _isLoading.postValue(false)
            _listOfSpecies.postValue(dataSet)
        }
    }

    fun filter(searchText: String) {
        val dataSetInner: ArrayList<Species> = arrayListOf()
        if (searchText.isNotEmpty()) {
            dataSet.forEach {
                if (it.name?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {
                    dataSetInner.add(it)
                }
            }
            _listOfSpecies.value = dataSetInner
        }
        else
            _listOfSpecies.value = dataSet
    }

    fun handleItemWhenClicked(context: Context ,item: Species) {
        val intent = Intent(context, ListPlant::class.java)
        intent.putExtra("species", item)
        context.startActivity(intent)
    }


    fun handleItemWhenLongClicked(item: Species) {

    }
}

