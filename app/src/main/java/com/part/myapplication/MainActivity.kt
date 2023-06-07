package com.part.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.part.myapplication.databinding.ActivityMainBinding
import com.part.myapplication.ui.Activities.ui.Login
import com.part.myapplication.ui.SQLLite.LoginDatabase
import com.part.myapplication.ui.SQLLite.Login_Resgister
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obj = SaveDataSqlLiteDB(this)
        obj.checkData()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val loginDatabase = LoginDatabase(this)
        val cursor = loginDatabase.RetrievedValues(this)
        // Retrieve the username and password from the intent extras
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        if (username != null && password != null) {
            val cursor = loginDatabase.RetrievedValues(this)
            while (cursor.moveToNext()) {
                val storedUsername = cursor.getString(cursor.getColumnIndex("Username"))
                val storedPassword = cursor.getString(cursor.getColumnIndex("Password"))
                if (username == storedUsername && password== storedPassword) {
                    val imageBlob = cursor.getBlob(cursor.getColumnIndex("Image"))
                    val name = cursor.getString(cursor.getColumnIndex("Username"))
                    // Set the retrieved image in @+id/imageView
                    val imageView: ImageView = binding.navView.getHeaderView(0).findViewById(R.id.imageView)
                    val bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.size)
                    imageView.setImageBitmap(bitmap)


                    // Set the retrieved name in @+id/textView
                    val textView: TextView = binding.navView.getHeaderView(0).findViewById(R.id.textView)
                    textView.text = name


                    break
                }
            }
        }

        binding.appBarMain.fab.setOnClickListener { view ->

            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_goals, R.id.nav_Timer,R.id.nav_Achievements_Screen,
                R.id.nav_Project_Task_Management_Screen,R.id.nav_UpdateFragment,R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}