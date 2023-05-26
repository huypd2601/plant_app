package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.app2.databinding.ActivityIntroductionPage2Binding

class IntroductionPage2 : AppCompatActivity() {
    private lateinit var binding:ActivityIntroductionPage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction_page2)
        binding.nextIntroButton2.setOnClickListener {
            val intent = Intent(this, IntroductionPage3::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }
}