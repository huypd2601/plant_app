package com.example.app2.species

import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.util.*
interface OnSpeciesItemListener {
    fun onClickItem(item: Species, view : View)
}


class SpeciesAdapter(private val itemListener: OnSpeciesItemListener) :
    ListAdapter<Species, SpeciesViewHolder>
        (ImageDiffUtil()) , SectionIndexer{

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpeciesViewHolder {
        return SpeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val species = getItem(position)
        holder.bindData(species, itemListener)
    }

    class ImageDiffUtil : DiffUtil.ItemCallback<Species>() {
        override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Species,
            newItem: Species
        ): Boolean {
            return oldItem.name == newItem.name
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






