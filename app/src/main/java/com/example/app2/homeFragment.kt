package com.example.app2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.app2.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class homeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        firestore.collection("profile").document("profile")
            .collection("profile").document(firebaseAuth.uid.toString())
            .get().addOnSuccessListener {
                val data : MutableMap<String,Any> = it.data!!
                val userName = data["UserName"].toString()
                val email = data["Email"].toString()
                binding.nameUser.text = userName
                val preferences = this.requireActivity()!!
                    .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.apply{
                    putString("UserName",userName)
                    putString("Email",email)
                }.apply()
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.species.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_homeFragment2_to_speciesFragment)
        }

        binding.articles.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_homeFragment2_to_articlesFragment)
        }
        binding.addingNew.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.addingFragment2)
        }
    }
}