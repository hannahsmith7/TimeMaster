package com.part.myapplication.ui.Fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.part.myapplication.R



class setting : Fragment() {
    private var avatarImageView: ImageView? = null

    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText
    private lateinit var submitBtn: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)


        // Initialize views

        // Initialize views
        avatarImageView = view.findViewById<ImageView>(R.id.avatarImageView)
        imageView2 = view.findViewById<ImageView>(R.id.imageView2)
        imageView3 = view.findViewById<ImageView>(R.id.imageView3)
        imageView4 = view.findViewById<ImageView>(R.id.imageView4)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        editText2 = view.findViewById<EditText>(R.id.editText2)
        editText3 = view.findViewById<EditText>(R.id.editText3)
        editText4 = view.findViewById<EditText>(R.id.editText4)
        submitBtn = view.findViewById<Button>(R.id.submitBtn)

        // Set click listeners
        restoreData()
        // Set click listeners
        avatarImageView!!.setOnClickListener { openImagePicker(1) }

        imageView2.setOnClickListener() {
            openImagePicker(2)
        }

        imageView3.setOnClickListener() {
            openImagePicker(3)
        }

        imageView4.setOnClickListener() {
            openImagePicker(4)
        }

        submitBtn.setOnClickListener() {
            saveData()
        }





        return view
    }

    private fun openImagePicker(requestCode: Int) {
        // TODO: Implement image picker logic here
        // Start an activity to pick an image and handle the result in onActivityResult()
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, requestCode)
    }


    private fun saveData() {
        // Save data to SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("MyPreferences", 0)
        val editor = sharedPreferences?.edit()
        editor?.putString("editText2", editText2.text.toString())
        editor?.putString("editText3", editText3.text.toString())
        editor?.putString("editText4", editText4.text.toString())

        // Save image URIs if they are not null
        val imageUri2 = imageView2.tag as? String
        val imageUri3 = imageView3.tag as? String
        val imageUri4 = imageView4.tag as? String
         val isTrue:Boolean=imageUri2 != null ||imageUri3 != null||imageUri4 != null
        if (imageUri2 != null) {
            editor?.putString("imageUri2", imageUri2)
        }
        if (imageUri3 != null) {
            editor?.putString("imageUri3", imageUri3)
        }
        if (imageUri4 != null) {
            editor?.putString("imageUri4", imageUri4)
        }
      if(isTrue){
          Toast.makeText(context,"Successfully saved (:",Toast.LENGTH_LONG).show()
      }

        if(!isTrue){
            Toast.makeText(context,"you need to select a picture before you click submit):",Toast.LENGTH_LONG).show()
        }
        editor?.apply()
    }

    private fun restoreData() {
        // Restore data from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("MyPreferences", 0)
        if (sharedPreferences != null) {
            editText2.setText(sharedPreferences.getString("editText2", ""))
            editText3.setText(sharedPreferences.getString("editText3", ""))
            editText4.setText(sharedPreferences.getString("editText4", ""))

            val imageUri2 = sharedPreferences.getString("imageUri2", null)
            val imageUri3 = sharedPreferences.getString("imageUri3", null)
            val imageUri4 = sharedPreferences.getString("imageUri4", null)

            if (imageUri2 != null) {
                imageView2.setImageURI(Uri.parse(imageUri2))
                imageView2.tag = imageUri2
            }
            if (imageUri3 != null) {
                imageView3.setImageURI(Uri.parse(imageUri3))
                imageView3.tag = imageUri3
            }
            if (imageUri4 != null) {
                imageView4.setImageURI(Uri.parse(imageUri4))
                imageView4.tag = imageUri4
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            when (requestCode) {
                1 -> {
                    avatarImageView!!.setImageURI(selectedImageUri)
                }
                2 -> {
                    imageView2.setImageURI(selectedImageUri)
                    imageView2.tag = selectedImageUri.toString()
                }
                3 -> {
                    imageView3.setImageURI(selectedImageUri)
                    imageView3.tag = selectedImageUri.toString()
                }
                4 -> {
                    imageView4.setImageURI(selectedImageUri)
                    imageView4.tag = selectedImageUri.toString()
                }
            }
        }
    }

}