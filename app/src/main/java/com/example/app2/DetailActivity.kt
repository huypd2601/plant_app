package com.example.app2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val plant: Plant? = intent.getParcelableExtra("plant")

        val plantName: TextView = findViewById(R.id.plant_name)
        val plantDesc: TextView = findViewById(R.id.plant_desc)
        val plantImage: ImageView = findViewById(R.id.plant_image)
        val plantKingdom: TextView = findViewById(R.id.plant_kingdom)
        val plantFamily: TextView = findViewById(R.id.plant_family)
        plantName.text = plant?.name
        plantDesc.text = plant?.desc
        Glide.with(this).load(plant?.image).into(plantImage)
        plantFamily.text = plant?.family
        plantKingdom.text = plant?.kingdom

    }
}
