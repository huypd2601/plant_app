package com.example.app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailPlantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plant: Plant? = arguments?.getParcelable("plant")

        val plantName: TextView = view.findViewById(R.id.plant_name)
        val plantDesc: TextView = view.findViewById(R.id.plant_desc)
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantKingdom: TextView = view.findViewById(R.id.plant_kingdom)
        val plantFamily: TextView = view.findViewById(R.id.plant_family)
        plantName.text = plant?.name
        plantDesc.text = plant?.desc
        Glide.with(this).load(plant?.image).into(plantImage)
        plantFamily.text = plant?.family
        plantKingdom.text = plant?.kingdom

        fun onCustomToggleClick(view: View) {
            Toast.makeText(requireContext(), "Custom", Toast.LENGTH_SHORT).show()
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_plant, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}