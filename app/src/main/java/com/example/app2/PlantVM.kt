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

private const val TAG = "PlantVM"

class PlantVM : ViewModel() {
    private val dataSet : ArrayList<Plant> = arrayListOf()
    private var _listOfPlant: MutableLiveData<List<Plant>> = MutableLiveData()
    val listOfPlants: LiveData<List<Plant>>
        get() = _listOfPlant

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
                        //dataSet.add(db.document.toObject<Plant>)
                        val plant : Plant = document.toObject(Plant::class.java)
                        //val plant : Plant = document.toObject(Plant)

                        println(plant)
                        dataSet.add(plant)
                        //Log.d("dataSet", "${document.toObject(Plant::class.java).plantName}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
            delay(3000)
            println("debug")
            println(dataSet.size)
            _isLoading.postValue(false)
            _listOfPlant.postValue(dataSet)
        }
    }

    fun filter(searchText: String) {
        val dataSetInner: ArrayList<Plant> = arrayListOf()
        if (searchText.isNotEmpty()) {
            dataSet.forEach {
                if (it.name?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {
                    dataSetInner.add(it)
                }
            }
            _listOfPlant.value = dataSetInner
        }
        else
            _listOfPlant.value = dataSet
    }

    fun handleItemWhenClicked(context: Context ,item: Plant) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("plant", item)
        context.startActivity(intent)
    }


    fun handleItemWhenLongClicked(item: Plant) {

    }
}