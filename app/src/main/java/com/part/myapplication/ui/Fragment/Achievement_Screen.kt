package com.part.myapplication.ui.Fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.part.myapplication.R
import com.part.myapplication.ui.calculations.calculatedValues
import com.part.myapplication.ui.tableValues.tableValuesSettings


class Achievement_Screen : Fragment() {
    private lateinit var img:ImageView
    private lateinit var txt:TextView

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_achievement__screen, container, false)
        //calling the class that handles table data
        val objTableValues = tableValuesSettings()
        //displaying table with the values from sqllite
        objTableValues.DisplayAllValuesInTable(view,activity,false )


        img = view.findViewById(R.id.img)
        txt =view.findViewById(R.id.txt)


        restoreData(view)
        return view
    }
    private fun restoreData(view:View) {
        // Restore data from sharepreference

        val sharedPreferences = activity?.getSharedPreferences("MyPreferences", 0)

        //retrieving rank achievement rate from sql lite
        val objPercentageAchieved = calculatedValues()

        val txtDecription= view.findViewById<TextView>(R.id.txtDecription)

        txtDecription.setText("Rank achievement "+ objPercentageAchieved.PercentageAchieved(view,activity).toString()+"%")

        //if the rank achievement rate is below 50% display this
        if(objPercentageAchieved.PercentageAchieved(view,activity)!! <= 50){
            //retrieving image from SharedPreferences
            img.setImageURI(Uri.parse(sharedPreferences?.getString("imageUri2", "")))
            txt.setText(sharedPreferences?.getString("editText2", ""))
        }
        if(objPercentageAchieved.PercentageAchieved(view,activity)!! in 51..74){
            img.setImageURI(Uri.parse(sharedPreferences?.getString("imageUri3", "")))
            txt.setText(sharedPreferences?.getString("editText3", ""))
        }

        if(objPercentageAchieved.PercentageAchieved(view,activity)!! >74){
            img.setImageURI(Uri.parse(sharedPreferences?.getString("imageUri4", "")))
            txt.setText(sharedPreferences?.getString("editText4", ""))
        }
        if (txt.text.isBlank()) {
            // Set default text if blank
            txt.text = "To set your own motivation words and picture, go to settings, upload image, and write your own motivation"
        }

        if (img.drawable == null) {
            // Set default image if blank
            img.setImageResource(R.drawable.avatars)
        }
    }



}
