package com.example.app2.articles

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.app2.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "DETAIL ARTICLE"
class DetailArticlesFragment : Fragment() {

    val args by navArgs <DetailArticlesFragmentArgs>()
    val db = Firebase.firestore
    var userId : String ?= null
    var articles : Articles ?= null
    var isLiked : Boolean ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articles  = args.articles
        println("in detail")
        println(articles)
        val preferences = this.requireActivity()!!
            .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        userId = preferences.getString("USERID", null)
        println(userId)
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail_articles, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

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


        val like_btn : ToggleButton? = view?.findViewById(R.id.like_button)
        Log.d("isLiked", isLiked.toString())
        like_btn?.isChecked = articles?.isLiked == true

        like_btn?.setOnClickListener {
            if (like_btn.isChecked){
                val data = hashMapOf(
                    "desc" to articles?.articleTitle.toString(),
                    "image" to articles?.articleImage.toString()
                )

                db.collection("profile")
                    .document("profile")
                    .collection("profile")
                    .document(userId.toString())
                    .collection("liked_articles")
                    .document(articles?.articleId.toString())
                    .set(data)
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }else {

                db.collection("profile")
                    .document("profile")
                    .collection("profile")
                    .document(userId.toString())
                    .collection("liked_articles")
                    .document(articles?.articleId.toString())
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

