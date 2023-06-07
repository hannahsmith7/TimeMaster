package com.part.myapplication.ui.Activities.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.part.myapplication.MainActivity
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.LoginDatabase

class Login : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvSignupLink = findViewById<Button>(R.id.tvSignupLink)


        // Initialize views
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)



        tvSignupLink.setOnClickListener(){
            val intent =Intent(this@Login,Registraction::class.java)
            startActivity(intent)
        }
        // Set click listener for login button
        loginButton.setOnClickListener { loginUser() }
    }



    private fun loginUser() {
        // Get the entered username and password
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Perform login validation
        if (validateLogin(username, password)) {
            // Update the boolean value in the database
            updateBoolValue(username, true)

            // Display a success message
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@Login, MainActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        } else {
            // Display an error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        // Implement your logic to validate the username and password
        // by querying the SQLite database
        val database = LoginDatabase(this)
        val cursor = database.RetrievedValues(this)
        while (cursor.moveToNext()) {
            val storedUsername = cursor.getString(cursor.getColumnIndexOrThrow("Username"))
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("Password"))
            if (username == storedUsername && password == storedPassword) {
                return true
            }
        }
        return false
    }

    private fun updateBoolValue(username: String, boolValue: Boolean) {
        // Implement your logic to update the boolean value in the database
        val database = LoginDatabase(this)
        val db = database.writableDatabase
        val cv = ContentValues()
        cv.put("bool", boolValue.toString())
        db.update("LoginDatabase_Table", cv, "Username=?", arrayOf(username))
    }
}
