package com.example.app2.articles

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
import androidx.navigation.fragment.findNavController
import com.example.app2.R
import com.example.app2.databinding.FragmentArticlesBinding
import java.util.*
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView


class ArticlesFragment : Fragment() {

    lateinit var binding: FragmentArticlesBinding
    lateinit var adapter: ArticlesAdapter
    lateinit var viewModel: ArticlesVM
    lateinit var searchView: SearchView
    lateinit var searchList: List<Articles>
    lateinit var dataList: List<Articles>
    var mRecyclerView: IndexFastScrollRecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticlesBinding.bind(view)
        viewModel = ViewModelProvider(this)[ArticlesVM::class.java]
        searchView = binding.search
        dataList = arrayListOf<Articles>()
        searchList = arrayListOf<Articles>()
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
        binding.backButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_articlesFragment_to_homeFragment2)
        }
    }

    private fun setUpRecyclerView() {
        binding.articlesListRecycleView.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView = binding.articlesListRecycleView
        adapter = ArticlesAdapter(OnArticlesClickListener)
        binding.articlesListRecycleView.adapter = adapter
        viewModel.loadData()
    }

    private val OnArticlesClickListener = object : OnArticlesItemListener {
        override fun onClickItem(item: Articles, view: View) {
            viewModel.handleItemWhenClicked(view ,item)
        }
    }

    private fun registerDataEvent() {
        viewModel.listOfArticles.observe(viewLifecycleOwner, Observer { data ->
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

}