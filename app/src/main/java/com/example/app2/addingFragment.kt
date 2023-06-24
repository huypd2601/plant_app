package com.example.app2


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app2.databinding.FragmentAddingBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val cameraRequest = 1888
    private lateinit var binding: FragmentAddingBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var bb:ByteArray
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        firestore = FirebaseFirestore.getInstance()
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddingBinding.inflate(inflater, container, false)
        imageView = binding.imageView
        button = binding.buttonUp
        val name = binding.plantName
        val family = binding.plantFamily
        val kingdom = binding.plantKingdom
        val decs = binding.plantDesc
        button.setOnClickListener {
            val data : MutableMap<String,Any> = HashMap()
            val updata = firestore.collection("species").document(family.text!!.trim().toString())
            data["family"] = family.text!!.trim().toString()
            updata.set(data)
            val ref = storageReference.child("${name.text!!.trim().toString()}.jpg")
            data["image"] = "https://firebasestorage.googleapis.com/v0/b/plantapp-278a7.appspot.com/o/${name.text!!.trim().toString()}.jpg?alt=media"
            data["desc"] = decs.text.trim().toString()
            data["kingdom"] = kingdom.text!!.trim().toString()
            data["name"] = name.text!!.trim().toString()
            updata.collection(family.text!!.trim().toString()).document(name.text!!.trim().toString())
                .set(data).addOnCompleteListener {
                    Toast.makeText(this.context,"Thêm dữ liệu thành công!",Toast.LENGTH_SHORT).show()
                    val controller = findNavController()
                    controller.navigate(R.id.action_addingFragment2_to_homeFragment2)
                }
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            if (resultCode == Activity.RESULT_OK){
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                val bytes: ByteArrayOutputStream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG,100,bytes)
                bb = bytes.toByteArray()
                imageView.setImageBitmap(photo)
            } else {
                val controller = findNavController()
                controller.navigate(R.id.homeFragment2)
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}