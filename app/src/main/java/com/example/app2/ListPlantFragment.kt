package com.example.app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app2.databinding.ActivityListPlantBinding
import com.example.app2.databinding.FragmentListPlantBinding
import com.example.app2.databinding.FragmentSpeciesBinding
import java.util.*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
    private lateinit var adapter: PlantAdapter
    private lateinit var viewModel: PlantVM
    private lateinit var searchView: SearchView
    private lateinit var searchList: List<Plant>
    private lateinit var dataList: List<Plant>
    val args by navArgs <ListPlantFragmentArgs>()

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
            viewModel.handleItemWhenClicked(view, item)
        }
    }

    private fun registerDataEvent() {
        viewModel.listOfPlants.observe(viewLifecycleOwner, Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}