package com.part.myapplication.ui.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB

// TODO: Rename parameter arguments, choose names that match



class UpdateFragment : Fragment() {

private lateinit var btnUpdate: Button
private lateinit var idTextView: TextView
private lateinit var comboBoxName: Spinner
private lateinit var completedComboBox: Spinner
private lateinit var dateEditText: EditText
private lateinit var trackHoursEditText: EditText
private lateinit var progressEditText: EditText




    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_update, container, false)
        // Inflate the layout for this fragment

        btnUpdate= view.findViewById(R.id.btnUpdate)
        idTextView= view.findViewById(R.id.idTextView)
        comboBoxName= view.findViewById(R.id.comboBoxName)
        completedComboBox= view.findViewById(R.id.completedComboBox)
        dateEditText= view.findViewById(R.id.dateEditText)
        trackHoursEditText= view.findViewById(R.id.trackHoursEditText)
        progressEditText= view.findViewById(R.id.progressEditText)



        // Initialize the database object
        val db = SaveDataSqlLiteDB(requireContext())

        // Retrieve names from the database
        val  names = db.retrieveNames()

        // Populate the comboBoxName with retrieved names
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, names)
        comboBoxName.adapter = adapter

        // Set a listener for comboBoxName selection
        comboBoxName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedName = names[position]
                val userDetails = db.getUserDetailsByName(selectedName)

                userDetails?.let {
                    idTextView.text = it.id.toString()
                    dateEditText.setText(it.date)
                    trackHoursEditText.setText(it.trackHours)
                    progressEditText.setText(it.progress)
                    completedComboBox.setSelection(if (it.completed == "Yes") 0 else 1)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Set a listener for btnUpdate click
        btnUpdate.setOnClickListener {
            val selectedName = comboBoxName.selectedItem.toString()
            val date = dateEditText.text.toString()
            val trackHours = trackHoursEditText.text.toString()
            val progress = progressEditText.text.toString()
            var completed = completedComboBox.selectedItem.toString()
if(progress.equals("100%")){
    completed ="Yes"
}
            db.updateDetails(selectedName, date, trackHours, progress, completed)
            Toast.makeText(requireContext(), "Value successfully updated", Toast.LENGTH_SHORT).show()
        }
        return  view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val db = SaveDataSqlLiteDB(requireContext())
        // Close the database connection
        db.close()
    }


}