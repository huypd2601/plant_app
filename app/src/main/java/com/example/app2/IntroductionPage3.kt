package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.app2.databinding.ActivityIntroductionPage3Binding

class IntroductionPage3 : AppCompatActivity() {
    private lateinit var binding:ActivityIntroductionPage3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction_page3)
        binding.nextIntroButton3.setOnClickListener {
            val intent = Intent(this, logInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}