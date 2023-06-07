package com.part.myapplication.ui.Activities.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.Login_Resgister
import java.io.ByteArrayOutputStream
import java.io.IOException

class Registraction : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var tvLoginLink: Button
    private lateinit var logo: ImageView
    private val obj = Login_Resgister()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registraction)

        userName = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvLoginLink = findViewById(R.id.tvLoginLink)
        logo = findViewById<ImageView>(R.id.logo)

        logo.setOnClickListener() {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            val IMAGE_REQUEST_CODE=1
            startActivityForResult(intent, IMAGE_REQUEST_CODE)

        }

        //when signup button is pressed we need to communicate with the sql lite database
        btnSignUp.setOnClickListener() {
            val usernameText = userName.text.toString()
            val passwordText = password.text.toString()
            val imageBlob = getImageBlobFromImageView(logo)

            if (usernameText.isNotEmpty() && passwordText.isNotEmpty() && imageBlob != null) {
                if (obj.insertValues(usernameText, passwordText, imageBlob, "false", this)) {
                    Toast.makeText(this, "$usernameText, you have successfully signed up (:",
                        Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@Registraction, Login::class.java))
                } else {
                    Toast.makeText(this, "Error. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
            }
        }
        tvLoginLink.setOnClickListener() {
            startActivity(Intent(this@Registraction, Login::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val PICK_IMAGE_REQUEST_CODE = 1
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            val imageUri = data.data

            try {
                // Set the selected image in the logo ImageView
                logo.setImageURI(imageUri)

                // Call the insertValues method with the selected image
                val inputStream = contentResolver.openInputStream(imageUri!!)
                val imageBlob = inputStream?.readBytes()
                inputStream?.close()

                // Pass the imageBlob in the insertValues method
                if (!userName.text.toString().isEmpty() && !password.text.toString().isEmpty()&& imageBlob!=null) {
                    if (obj.insertValues(
                            userName.text.toString(),
                            password.text.toString(),
                            imageBlob,
                            "false",
                            this
                        )
                    ) {
                        Toast.makeText(
                            this, "${userName.text} you have successfully signed up (:",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@Registraction, Login::class.java))
                    } else {
                        Toast.makeText(this, "Error. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun getImageBlobFromImageView(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
        return null
    }
}