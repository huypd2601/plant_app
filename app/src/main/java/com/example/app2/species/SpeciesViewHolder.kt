package com.example.app2.species

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R

class SpeciesViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): SpeciesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.species_items,
                    parent,
                    false
                )
            return SpeciesViewHolder(view)
        }
    }

    fun bindData(species: Species, callback: OnSpeciesItemListener) {
        val speciesName = itemView.findViewById<TextView>(R.id.species_name);
        speciesName.text = species.name
        itemView.setOnClickListener { callback.onClickItem(species, itemView) }
    }
}
