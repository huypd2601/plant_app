package com.example.app2.articles

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

class DetailArticlesFragment : Fragment() {

    val args by navArgs <DetailArticlesFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail_articles, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val articles : Articles? = args.articles
        println("in detail")
        println(articles)

        val articlesTitle = itemView.findViewById<TextView>(R.id.article_title)
        val articlesImage = itemView.findViewById<ImageView>(R.id.article_image)
        val authorName =  itemView.findViewById<TextView>(R.id.author_name)
        val authorAva = itemView.findViewById<ImageView>(R.id.author_ava)
        val authorDesc = itemView.findViewById<TextView>(R.id.author_desc)

        articlesTitle.text = articles?.articleTitle
        authorName.text = articles?.authorName
        authorDesc.text = articles?.authorDesc
        Glide.with(this).load(articles?.articleImage)
            .into(articlesImage)
        Glide.with(this).load(articles?.authorAva)
            .into(authorAva)

        val bnt : Button? = view?.findViewById(R.id.backButton)
        bnt?.setOnClickListener {
//            getFragmentManager()?.popBackStack()
            val controller = findNavController()
//            controller.navigate(R.id.action_detailArticlesFragment_to_articlesFragment)
            findNavController().popBackStack()
        }


    }



}

