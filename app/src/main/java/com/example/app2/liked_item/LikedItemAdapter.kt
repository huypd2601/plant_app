package com.example.app2.liked_item

import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


import java.util.ArrayList
import java.util.HashMap

interface OnLikedItemListener {
    fun onClickItem(item: LikedItem, view : View)
}


class LikedItemAdapter(private val itemListener: OnLikedItemListener) :
    ListAdapter<LikedItem, LikedItemViewHolder>
        (ImageDiffUtil()) , SectionIndexer {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikedItemViewHolder {
        return LikedItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LikedItemViewHolder, position: Int) {
        val LikedItem = getItem(position)
        holder.bindData(LikedItem, itemListener)
    }

    class ImageDiffUtil : DiffUtil.ItemCallback<LikedItem>() {
        override fun areItemsTheSame(oldItem: LikedItem, newItem: LikedItem): Boolean {
            return oldItem.desc == newItem.desc
        }

        override fun areContentsTheSame(
            oldItem: LikedItem,
            newItem: LikedItem
        ): Boolean {
            return oldItem.desc == newItem.desc
        }

    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val alphabetFull = ArrayList<String>()
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionIndex]
    }

}
