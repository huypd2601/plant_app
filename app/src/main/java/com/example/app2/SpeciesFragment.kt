package com.example.app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.app2.databinding.ActivitySpeciesBinding
import com.example.app2.databinding.FragmentSpeciesBinding
import java.util.*
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpeciesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeciesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var binding: FragmentSpeciesBinding
    lateinit var adapter: SpeciesAdapter
    lateinit var viewModel: SpeciesVM
    lateinit var searchView: SearchView
    lateinit var searchList: List<Species>
    lateinit var dataList: List<Species>
    var mRecyclerView: IndexFastScrollRecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSpeciesBinding.bind(view)
        viewModel = ViewModelProvider(this)[SpeciesVM::class.java]
        searchView = binding.search
        dataList = arrayListOf<Species>()
        searchList = arrayListOf<Species>()
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
        initialiseUI()
        registerDataEvent()
    }

    private fun setUpRecyclerView() {
        binding.speciesListRecycleView.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView = binding.speciesListRecycleView
        adapter = SpeciesAdapter(OnSpeciesClickListener)
        binding.speciesListRecycleView.adapter = adapter
        viewModel.loadData()
    }

    private val OnSpeciesClickListener = object : OnSpeciesItemListener {
        override fun onClickItem(item: Species, view: View) {
            viewModel.handleItemWhenClicked(view ,item)
        }
    }

    private fun registerDataEvent() {
        viewModel.listOfSpecies.observe(viewLifecycleOwner, Observer { data ->
            run {
                adapter.submitList(data)
            }
        })
    }

    private fun initialiseUI() {
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setIndexTextSize(12)
            setIndexBarCornerRadius(0)
            setIndexBarTransparentValue(0.4.toFloat())
            setPreviewPadding(0)
            setPreviewTextSize(60)
            setPreviewTransparentValue(0.6f)
            setIndexBarVisibility(true)
            setIndexBarStrokeVisibility(true)
            setIndexBarStrokeWidth(1)
            setIndexBarHighLightTextVisibility(true)
        }
        Objects.requireNonNull<RecyclerView.LayoutManager>(mRecyclerView?.layoutManager)
            .scrollToPosition(0)
    }


   //--------------------------------------//

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_species, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment SpeciesFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SpeciesFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}