package com.example.app2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.SectionIndexer
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "SpeciesAdapter"



class SpeciesAdapter(private val mDataArray: ArrayList<String>?,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SpeciesAdapter.ViewHolder>(), SectionIndexer {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    override fun getItemCount(): Int {
        return mDataArray?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.species_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView.text = mDataArray!![position]
//        holder.mTextView.setOnClickListener {
//            click1(context)
//        }
    }


    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = mDataArray!!.size
            Log.d(TAG, "Error getting documents: $size")
            while (i < size) {
                val section = mDataArray[i][0].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
//        sectionsTranslator = sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var mTextView: TextView

        init {
            mTextView = itemView.findViewById(R.id.tv_alphabet)
        }

        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.OnItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun OnItemClick(position: Int)
    }
}