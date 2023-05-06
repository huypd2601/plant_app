package com.example.app2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView

class PlantViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view =
                layoutInflater.inflate(
                    R.layout.plant_view,
                    parent,
                    false
                )
            return PlantViewHolder(view)
        }
    }

    fun bindData(plant: Plant, callback: OnPlantItemListener) {
        val plantName = itemView.findViewById<TextView>(R.id.plant_name);
        val plantDesc =  itemView.findViewById<TextView>(R.id.plant_desc)
        val plantImage = itemView.findViewById<ImageView>(R.id.plant_image)
        val plantKingdom =  itemView.findViewById<TextView>(R.id.plant_kingdom)
        val plantFamily = itemView.findViewById<TextView>(R.id.plant_family)

        plantName.text = plant.name
        plantDesc.text = plant.desc
        plantKingdom.text = plant.kingdom
        plantFamily.text  = plant.family
        Glide.with(itemView.context).load(plant.image).centerCrop()
            .into(plantImage)

        itemView.setOnClickListener { callback.onClickItem(plant) }
        itemView.setOnLongClickListener {
            callback.onLongClick(plant)
            true
        }
    }
}
