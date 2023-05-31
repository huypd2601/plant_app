package com.example.app2

//import androidx.appcompat.widget.SearchView
import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app2.databinding.ActivityListPlantBinding
import java.util.*


class ListPlant : AppCompatActivity() {
    lateinit var binding: ActivityListPlantBinding
    lateinit var adapter: PlantAdapter
    lateinit var viewModel: PlantVM
    lateinit var searchView: SearchView
    lateinit var searchList: List<Plant>
    lateinit var dataList: List<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding   = DataBindingUtil.setContentView(this,R.layout.activity_list_plant)
        viewModel = ViewModelProvider(this)[PlantVM::class.java]
        searchView = findViewById(R.id.search)
        dataList = arrayListOf<Plant>()
        searchList = arrayListOf<Plant>()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
        binding.plantListRecycleView.layoutManager = LinearLayoutManager(this);

        adapter = PlantAdapter(OnPlantClickListener)
        binding.plantListRecycleView.adapter = adapter
        val species: Species? = intent.getParcelableExtra("species")
        println(species)
        binding.textSpecies.text    =   species?.name
        viewModel.loadData(species)
    }

    private val OnPlantClickListener  = object : OnPlantItemListener {
        override fun onClickItem(item: Plant) {
            viewModel.handleItemWhenClicked(this@ListPlant,item)
        }
    }

    private fun registerDataEvent() {
        viewModel.listOfPlants.observe(this, Observer{ data ->
            run{
                adapter.submitList(data)
            }
        })
    }


}


