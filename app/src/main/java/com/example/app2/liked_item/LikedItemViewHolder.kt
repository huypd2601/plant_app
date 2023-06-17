package com.example.app2.liked_item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app2.R

class LikedItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): LikedItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.liked_view,
                    parent,
                    false
                )
            return LikedItemViewHolder(view)
        }
    }

    fun bindData(likedItem: LikedItem, callback: OnLikedItemListener) {
        val likedItemDesc = itemView.findViewById<TextView>(R.id.liked_item_desc)
        val likedItemImage = itemView.findViewById<ImageView>(R.id.liked_item_image)

        likedItemDesc.text = likedItem.desc
        Glide.with(itemView.context).load(likedItem.image).centerCrop()
            .into(likedItemImage)
        itemView.setOnClickListener { callback.onClickItem(likedItem, itemView) }
    }
}