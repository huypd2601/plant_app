package com.example.app2

//import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.databinding.ActivitySpeciesBinding
import java.util.*
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView


class SpeciesActivity : AppCompatActivity() {
//    lateinit var binding: ActivitySpeciesBinding
//    lateinit var adapter: SpeciesAdapter
//    lateinit var viewModel: SpeciesVM
//    lateinit var searchView: SearchView
//    lateinit var searchList: List<Species>
//    lateinit var dataList: List<Species>
//    var mRecyclerView: IndexFastScrollRecyclerView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding   = DataBindingUtil.setContentView(this,R.layout.activity_species)
//        viewModel = ViewModelProvider(this)[SpeciesVM::class.java]
//        searchView = findViewById(R.id.search)
//        dataList = arrayListOf<Species>()
//        searchList = arrayListOf<Species>()
//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                return true
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                val searchText = newText!!.toLowerCase(Locale.getDefault())
//                viewModel.filter(searchText)
//                registerDataEvent()
//                return false
//            }
//        })
//
//        setUpRecyclerView()
//        initialiseUI()
//        registerDataEvent()
//
//    }
//
//    private fun setUpRecyclerView() {
//        binding.speciesListRecycleView.layoutManager = LinearLayoutManager(this);
//        mRecyclerView = findViewById(R.id.species_list_recycle_view)
//        adapter = SpeciesAdapter(OnSpeciesClickListener)
//        binding.speciesListRecycleView.adapter = adapter
//        viewModel.loadData()
//    }
//
//    private val OnSpeciesClickListener  = object : OnSpeciesItemListener {
//        override fun onClickItem(item: Species) {
//            viewModel.handleItemWhenClicked(this@SpeciesActivity,item)
//        }
//    }
//
//    private fun registerDataEvent() {
//        viewModel.listOfSpecies.observe(this, Observer{ data ->
//            run{
//                adapter.submitList(data)
//            }
//        })
//    }
//
//    private fun initialiseUI() {
//        mRecyclerView?.apply {
//            layoutManager = LinearLayoutManager(this@SpeciesActivity)
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





