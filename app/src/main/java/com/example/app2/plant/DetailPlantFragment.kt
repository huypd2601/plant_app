package com.example.app2.plant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.app2.R

class DetailPlantFragment : Fragment() {

    val args by navArgs <DetailPlantFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail_plant, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plant : Plant? = args.plant
        print("in detail")
        print(plant)

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


    }



}

