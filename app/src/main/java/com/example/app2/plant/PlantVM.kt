package com.example.app2.plant


import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.app2.species.Species
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "PlantVM"
private const val TAG1 = "PlantVM1"

class PlantVM : ViewModel() {
    private val dataSet : ArrayList<Plant> = arrayListOf()
    private var _listOfPlant: MutableLiveData<List<Plant>> = MutableLiveData()
    val listOfPlants: LiveData<List<Plant>>
        get() = _listOfPlant

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadData(species: Species?) {
        _isLoading.postValue(true)

        viewModelScope.launch {

           // val dataSet = DataStore.getDataSet()
            dataSet.clear()
            // firebase
            val db = Firebase.firestore
//            val db1 = Firebase.firestore
//            db.collection("species")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        //dataSet.add(db.document.toObject<Plant>)
//                        val plant : Plant = document.toObject(Plant::class.java)
//                        //val plant : Plant = document.toObject(Plant)
//
//                        println(plant)
//                        dataSet.add(plant)
//                        //Log.d("dataSet", "${document.toObject(Plant::class.java).plantName}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "Error getting documents: ", exception)
//                }

//            db.collection("species")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        val species : String = document.id;
//                        db1.collection("species")
//                            .document("$species")
//                            .collection("$species")
//                            .get()
//                            .addOnSuccessListener  { result1 ->
//                                for (document1 in result1){
//                                    Log.d(TAG1, "${document1.id} => ${document1.data}")
//                                    val plant : Plant = document1.toObject(Plant::class.java)
//                                    println(plant)
//                                    dataSet.add(plant)
//                                }
//                            }
//                            .addOnFailureListener { exception ->
//                                Log.d(TAG1, "Error getting documents: ", exception)
//                            }
//
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "Error getting documents: ", exception)
//                }

            db.collection("species")
                .document("${species?.name}")
                .collection("${species?.name}")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val plant : Plant = document.toObject(Plant::class.java)
                        println(plant)
                        dataSet.add(plant)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG1, "Error getting documents: ", exception)
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

    fun handleItemWhenClicked(view: View, item: Plant) {

        val action = ListPlantFragmentDirections.actionFragmentListPlantToPlantDetailFragment(item)
        view.findNavController().navigate(action)

    }


    fun handleItemWhenLongClicked(item: Plant) {

    }
}

