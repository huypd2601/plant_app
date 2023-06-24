package com.example.app2.plant

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.app2.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
private const val TAG = "DETAIL PLANT"
class DetailPlantFragment : Fragment() {
    var userId : String ?= null
    val args by navArgs <DetailPlantFragmentArgs>()
    val db = Firebase.firestore
    var plant : Plant ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        plant  = args.plant
        print("in detail")
        print(plant)
        // Inflate the layout for this fragment
        val preferences = this.requireActivity()!!
            .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        userId = preferences.getString("USERID", null)
        println(userId)
        // Inflate the layout for this fragment
        db.collection("profile")
            .document("profile")
            .collection("profile")
            .document(userId.toString())
            .collection("liked_plants")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    if (document.id == plant?.name.toString()) {
                        println(document.id)
                        // like button active
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return inflater.inflate(R.layout.fragment_detail_plant, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantName: TextView? = view.findViewById(R.id.plant_name)
        val plantDesc: TextView? = view.findViewById(R.id.plant_desc)
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantKingdom: TextView? = view.findViewById(R.id.plant_kingdom)
        val plantFamily: TextView? = view.findViewById(R.id.plant_family)

        plantName?.text = plant?.name
        plantDesc?.text = plant?.desc
        Glide.with(this).load(plant?.image).into(plantImage)
        plantFamily?.text = plant?.family
        plantKingdom?.text = plant?.kingdom

        val bnt : Button? = view.findViewById(R.id.backButton)
        bnt?.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_plantDetailFragment_to_homeFragment2)
        }

        val like_btn : ToggleButton? = view.findViewById(R.id.like_button)
        like_btn?.isChecked = plant?.isLiked == true
        like_btn?.setOnClickListener {
            if (like_btn.isChecked) {
                val data = hashMapOf(
                    "desc" to plant?.name.toString(),
                    "image" to plant?.image.toString()
                )

                db.collection("profile")
                    .document("profile")
                    .collection("profile")
                    .document(userId.toString())
                    .collection("liked_plants")
                    .document(plant?.name.toString())
                    .set(data)
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            } else {

                db.collection("profile")
                    .document("profile")
                    .collection("profile")
                    .document(userId.toString())
                    .collection("liked_plants")
                    .document(plant?.name.toString())
                    .delete()
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }
        }

    }



}

