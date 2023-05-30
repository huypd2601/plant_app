package com.example.app2

import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "SpeciesActivity"

class SpeciesActivity {
//    : AppCompatActivity(), SpeciesAdapter.OnItemClickListener{
//    var mRecyclerView: IndexFastScrollRecyclerView? = null
//
//    private val speciesNameList : ArrayList<String> = arrayListOf()
//    private val db = Firebase.firestore
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_species)
//        mRecyclerView = findViewById(R.id.fast_scroller_recycler)
//
//        db.collection("species")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    val species : String = document.id;
//                    //println(species)
//                    speciesNameList.add(species)
//                }
//                initialiseUI()
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "Error getting documents: ", exception)
//            }
//    }
//
//    override fun OnItemClick(position: Int) {
//
//    }
//
//    private fun initialiseUI() {
//        mRecyclerView?.apply {
//            layoutManager = LinearLayoutManager(this@SpeciesActivity)
//            adapter = SpeciesAdapter(speciesNameList, listener : this)
//            setIndexTextSize(12)
//            setIndexBarCornerRadius(0)
//            setIndexBarTransparentValue(0.4.toFloat())
//            setPreviewPadding(0)
//            setPreviewTextSize(60)
//            setPreviewTransparentValue(0.6f)
//            setIndexBarVisibility(true)
//            setIndexBarStrokeVisibility(true)
//            setIndexBarStrokeWidth(1)
//            setIndexBarHighLightTextVisibility(true)
//        }
//        Objects.requireNonNull<RecyclerView.LayoutManager>(mRecyclerView?.layoutManager)
//            .scrollToPosition(0)
//    }
}