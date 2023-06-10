package com.example.app2.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app2.R

class ArticlesViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): ArticlesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.articles_view,
                    parent,
                    false
                )
            return ArticlesViewHolder(view)
        }
    }

    fun bindData(articles: Articles, callback: OnArticlesItemListener) {
        val articlesTitle = itemView.findViewById<TextView>(R.id.article_title)
        val articlesImage = itemView.findViewById<ImageView>(R.id.articles_image)
        val authorName =  itemView.findViewById<TextView>(R.id.author_name)
        val authorAva = itemView.findViewById<ImageView>(R.id.author_ava)
        val authorDesc = itemView.findViewById<TextView>(R.id.author_desc)

        articlesTitle.text = articles.articleTitle
        authorName.text = articles.authorName
        authorDesc.text = articles.authorDesc
        Glide.with(itemView.context).load(articles.articleImage).centerCrop()
            .into(articlesImage)
        Glide.with(itemView.context).load(articles.authorAva).centerCrop()
            .into(authorAva)
        itemView.setOnClickListener { callback.onClickItem(articles, itemView) }
    }
}

