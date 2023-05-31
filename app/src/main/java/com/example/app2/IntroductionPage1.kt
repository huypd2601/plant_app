package com.example.app2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.app2.databinding.ActivityIntroductionPage1Binding


class IntroductionPage1 : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionPage1Binding
    lateinit var preferences: SharedPreferences
    val introKey ="Intro"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction_page1)
        preferences = getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)
        if (!preferences.getBoolean(introKey,true)){
            val intent = Intent(this, logInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
        binding.nextIntroButton1.setOnClickListener {
            preferences.edit().putBoolean(introKey,false).apply()
            val intent = Intent(this, IntroductionPage2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }
}