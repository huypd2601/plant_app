package com.example.app2.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.app2.MainActivity
import com.example.app2.R
import com.example.app2.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class logInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        val prefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = prefs.getBoolean("RememberLogin", true)
        if (!editor) {
            val user = firebaseAuth.currentUser
            if (user != null) {
                if (user.isEmailVerified) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.trim().toString()
            val pass = binding.password.text?.trim().toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val check = binding.rememberMe.isChecked
                        val user = firebaseAuth.currentUser
                        if (user?.isEmailVerified == true) {
                            val prefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                            val editor = prefs.edit()
                            editor.apply{
                                putString("USERID",firebaseAuth.uid.toString())
                                if (check){
                                    putBoolean("RememberLogin",false)
                                }
                            }.apply()
                            println(firebaseAuth.uid.toString())

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Vui lòng xác thực tài khoản!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(this, "Không được để trống !", Toast.LENGTH_SHORT).show()

            }
        }
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, signUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this, forgotPasswordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}