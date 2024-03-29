package com.example.app2.plant

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app2.R
import com.example.app2.species.Species
import com.example.app2.databinding.FragmentListPlantBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
    private lateinit var adapter: PlantAdapter
    private lateinit var viewModel: PlantVM
    private lateinit var searchView: SearchView
    private lateinit var searchList: List<Plant>
    private lateinit var dataList: List<Plant>
    val args by navArgs <ListPlantFragmentArgs>()
    val db = Firebase.firestore
    var isLiked : Boolean ?= null
    var userId : String ?= null
    private val TAG = "LIST PLANT FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = this.requireActivity()!!
            .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        userId = preferences.getString("USERID", null).toString()
        binding = FragmentListPlantBinding.bind(view)
        viewModel = ViewModelProvider(this)[PlantVM::class.java]
        searchView = binding.search
        dataList = arrayListOf<Plant>()
        searchList = arrayListOf<Plant>()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                viewModel.filter(searchText)
                registerDataEvent()
                return false
            }
        })
        setUpRecyclerView()
        registerDataEvent()
        registerLoadingView()
        binding.backButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_fragmentListPlant_to_speciesFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.plantListRecycleView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PlantAdapter(OnPlantClickListener)
        binding.plantListRecycleView.adapter = adapter

        val species : Species? = args.species
        binding.textSpecies.text = species?.name
        viewModel.loadData(species)

    }

    private val OnPlantClickListener = object : OnPlantItemListener {
        override fun onClickItem(item: Plant, view: View) {
            db.collection("profile")
                .document("profile")
                .collection("profile")
                .document(userId.toString())
                .collection("liked_plants")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        if (document.id == item?.name.toString()) {
                            println(document.id)
                            item.isLiked = true
                            break
                            // like button active
                        }
                        item.isLiked = false
                    }
                    viewModel.handleItemWhenClicked(view ,item)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }

        }
    }

    private fun registerDataEvent() {
        viewModel.listOfPlants.observe(viewLifecycleOwner, Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }
    private fun registerLoadingView() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            run {
                binding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else
                        View.INVISIBLE
            }
        }
    }

}