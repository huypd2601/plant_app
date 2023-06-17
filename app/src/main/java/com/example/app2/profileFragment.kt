package com.example.app2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.articles.Articles
import com.example.app2.articles.ArticlesAdapter
import com.example.app2.articles.ArticlesVM
import com.example.app2.articles.OnArticlesItemListener
import com.example.app2.databinding.FragmentArticlesBinding
import com.example.app2.databinding.FragmentProfileBinding
import com.example.app2.liked_item.LikedItem
import com.example.app2.liked_item.LikedItemAdapter
import com.example.app2.liked_item.LikedItemVM
import com.example.app2.liked_item.LikedItemViewModelFactory
import com.example.app2.liked_item.OnLikedItemListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView
import java.io.File
import java.util.Locale
import java.util.Objects

class profileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var adapter: LikedItemAdapter
    lateinit var viewModel: LikedItemVM
    private val PICK_IMAGE_REQUEST = 71
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageView: ImageView
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private var filePath: Uri? = null
    val db = Firebase.firestore
    var isLiked : Boolean ?= null
    var userId : String ?= null
    private var opt : Boolean ?= null
    private val TAG = "PROFILE FRAGMENT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        firestore = FirebaseFirestore.getInstance()

    }

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val userName = view.findViewById(R.id.tv_nameUser) as TextView
        val email = view.findViewById(R.id.tv_emailUser) as TextView
        firestore.collection("profile").document("profile")
            .collection("profile").document(firebaseAuth.uid.toString())
            .get().addOnSuccessListener {
                val data : MutableMap<String,Any> = it.data!!
                userName.text = data["UserName"].toString()
                email.text = data["Email"].toString()
            }
        imageView = view.findViewById(R.id.iv_avatarProfile)
        val ref = storageReference.child(firebaseAuth.uid.toString())
        ref.getBytes(10 * 1024 * 1024).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            imageView.setImageBitmap(bitmap)
        }
        imageView.setOnClickListener {
            launchGallery()
        }
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data!!.data
            imageView.setImageURI(filePath)
            val ref = storageReference.child(firebaseAuth.uid.toString())
            ref.putFile(filePath!!)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = this.requireActivity()!!
            .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        userId = preferences.getString("USERID", null).toString()
        binding = FragmentProfileBinding.bind(view)
        viewModel = ViewModelProvider(this)[LikedItemVM::class.java]

        setUpRecyclerView(true,userId!!)
        registerDataEvent()
        registerLoadingView()
        binding.plantsButton.setOnClickListener{
            Log.d("profileFragment", "plantButton ")
            opt = false
            Log.d("profileFragment", opt.toString())
            setUpRecyclerView(opt!!,userId!!)

        }
        binding.articlesButton.setOnClickListener{
            Log.d("profileFragment", "articlesButton ")
            opt = true
            Log.d("profileFragment", opt.toString())
            setUpRecyclerView(opt!!, userId!!)

        }
    }

    private fun setUpRecyclerView(opt : Boolean, userId : String) {
        binding.likedListRecycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LikedItemAdapter(OnLikedItemListener)
        binding.likedListRecycleView.adapter = adapter
        viewModel.loadData(opt, userId)

    }

    private val OnLikedItemListener = object : OnLikedItemListener {
        override fun onClickItem(item: LikedItem, view: View) {
            viewModel.handleItemWhenClicked(view ,item)
        }
    }

    private fun registerDataEvent() {
        viewModel.listOfLikedItem.observe(viewLifecycleOwner, Observer { data ->
            run {
                Log.d("profileFragment", "submit List")
                adapter.submitList(data)
//                println(data)
            }
        })

    }

    private fun registerLoadingView() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            run {
                binding.progressBar.visibility =
                    if (isLoading) View.VISIBLE else
                        View.INVISIBLE
            }
        }
    }


}