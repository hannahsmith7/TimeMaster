package com.part.myapplication.ui.Fragment

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.mikephil.charting.charts.PieChart
import com.part.myapplication.R
import com.part.myapplication.ui.Graph.populateGraph
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB
import com.part.myapplication.ui.tableValues.tableValuesSettings
import java.util.Date


class GoalScreenFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goal_screen, container, false)

        val lnAddNewGoal = view.findViewById<LinearLayout>(R.id.ln)
        lnAddNewGoal.setOnClickListener {
            showAddNewGoalDialog(view)
        }
        //calling the class that handles table data
        val objTableValues = tableValuesSettings()
        //displaying table with the values from sqllite
        objTableValues.DisplayAllValuesInTable(view, activity, false)
        val ln = view.findViewById<LinearLayout>(R.id.ln)
        ln.setOnClickListener(){
            showAddNewGoalDialog(view)
        }

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        val populateGraph=  populateGraph()

        populateGraph.upcommingPieGraph(view,pieChart)
        return view
    }

    @SuppressLint("MissingInflatedId")

    private fun showAddNewGoalDialog(view:View) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_goal, null)
        var saveDataSqlLiteDB = SaveDataSqlLiteDB(context)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Add New Goal")
            .setPositiveButton("Add") { dialog, which ->
                val etName = dialogView.findViewById<EditText>(R.id.etName)
                val etDate = dialogView.findViewById<DatePicker>(R.id.etDate)
                val etTrackHours = dialogView.findViewById<EditText>(R.id.etTrackHours)
                val etProgress = dialogView.findViewById<EditText>(R.id.etProgress)
                val etCompleted = dialogView.findViewById<Spinner>(R.id.etCompleted)

                val name = etName.text.toString()
                val day = etDate.dayOfMonth
                val month = etDate.month + 1
                val year = etDate.year
                val date = "$day/$month/$year"
                val trackHours = etTrackHours.text.toString() + "h"
                val progress = etProgress.text.toString() + "%"
                var completed = etCompleted.selectedItem.toString()
                if(name.isEmpty() || etTrackHours.text.isEmpty() || etProgress.text.isEmpty()){
                    Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_SHORT)
                        .show()

                }
                if(!progress.equals("100%")){
                    completed ="No".toString()
                }else{
                    completed ="Yes".toString()
                }
                if(name.isNotEmpty() && etTrackHours.text.isNotEmpty() && etProgress.text.isNotEmpty()) {
                    saveDataSqlLiteDB.insertData(name, date, trackHours, progress, completed)
                    Toast.makeText(requireContext(), "Goal added successfully!", Toast.LENGTH_SHORT)
                        .show()


                }
            }
                    .setNegativeButton("Cancel", null)

                val dialog = dialogBuilder.create()
                dialog.show()
            }

    override fun onDestroyView() {
        super.onDestroyView()
        var saveDataSqlLiteDB = SaveDataSqlLiteDB(context)
        saveDataSqlLiteDB.close()
    }

}

