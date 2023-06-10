package com.example.app2.plant

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


interface OnPlantItemListener {
    fun onClickItem(item: Plant, view: View)
}


class PlantAdapter (private val itemListener : OnPlantItemListener) :
    ListAdapter<Plant, PlantViewHolder>
        (ImageDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        return PlantViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bindData(plant, itemListener)
    }

    class ImageDiffUtil : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Plant,
            newItem: Plant
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }

}


