package com.example.app2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
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
import com.example.app2.login.logInActivity
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val userName = binding.tvNameUser
        val email = binding.tvEmailUser
        val preferences = this.requireActivity()!!
            .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        userName.text = preferences.getString("UserName",null).toString()
        email.text = preferences.getString("Email",null).toString()
        imageView = binding.ivAvatarProfile
        val ref = storageReference.child(firebaseAuth.uid.toString())
        ref.getBytes(10 * 1024 * 1024).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            imageView.setImageBitmap(bitmap)
        }
        imageView.setOnClickListener {
            launchGallery()
        }
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
            binding.plantsButton.setBackgroundResource(R.drawable.profile_btn)
            binding.articlesButton.setBackgroundResource(R.drawable.profile_btn_none)
            Log.d("profileFragment", "plantButton ")
            opt = false
            Log.d("profileFragment", opt.toString())
            setUpRecyclerView(opt!!,userId!!)
        }
        binding.articlesButton.setOnClickListener{
            binding.plantsButton.setBackgroundResource(R.drawable.profile_btn_none)
            binding.articlesButton.setBackgroundResource(R.drawable.profile_btn)
            Log.d("profileFragment", "articlesButton ")
            opt = true
            Log.d("profileFragment", opt.toString())
            setUpRecyclerView(opt!!, userId!!)
        }

        binding.ivMoreInformation.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(requireContext(),binding.ivMoreInformation)
            popupMenu.menuInflater.inflate(R.menu.sign_out,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                if (item.itemId == R.id.about ) {

                }
                if (item.itemId == R.id.signout ) {
                    val preferences = this.requireActivity()!!
                        .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    preferences.edit().apply(){
                        putBoolean("RememberLogin",true)
                    }.apply()
                    firebaseAuth.signOut()
                    val intent = Intent(requireContext(), logInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                true
            })
            popupMenu.show()
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