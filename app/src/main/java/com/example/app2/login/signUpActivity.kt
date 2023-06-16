package com.example.app2.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.app2.R
import com.example.app2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class signUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: signUpViewModel
    private lateinit var database: DatabaseReference
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this).get(signUpViewModel::class.java)
        database = Firebase.database.getReference("User")
        firestore = FirebaseFirestore.getInstance()
        binding.SignUpButton.setOnClickListener {
            val user = binding.username.text.trim().toString()
            val email = binding.email.text.trim().toString()
            val pass = binding.password.text?.trim().toString()
            val repass = binding.confirmpassword.text?.trim().toString()

            viewModel.checkEmailAndPassword(user,email, pass, repass)
        }
        listenerSuccessEvent()
        listenerErrorEvent()


    }
    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(this) { isSuccess ->
            if (isSuccess) {
                firebaseAuth = FirebaseAuth.getInstance()
                val email = binding.email.text.trim().toString()
                val pass = binding.password.text?.trim().toString()
                val name = binding.username.text.trim().toString()
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener { it ->
                    if (it.result.signInMethods?.size  == 0) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { it ->
                            if (it.isSuccessful) {
                                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                    if (it.isSuccessful){
                                        val data : MutableMap<String,Any> = HashMap()
                                        data["Email"] = email
                                        data["Password"] = pass
                                        data["UserName"] = name
                                        firestore.collection("profile").document("profile")
                                            .collection("profile").document(firebaseAuth.uid.toString()).set(data)

                                        Toast.makeText(this, "Đăng ký thành công, vui lòng kiểm tra email để xác thực tài khoản!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                val intent = Intent(this, logInActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                            }
                        }
                    }
                    else{
                        Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun listenerErrorEvent() {
        viewModel.isErrorEvent.observe(this) { errMsg ->
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        }
    }
}