package com.part.myapplication.ui.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.part.myapplication.R
import com.part.myapplication.ui.Graph.populateGraph
import com.part.myapplication.ui.tableValues.tableValuesSettings


class Project_Task_Management : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project__task__management, container, false)
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        //calling the class that handles table data
        val objTableValues = tableValuesSettings()
        //displaying table with the values from sqllite
       objTableValues.DisplayAllValuesInTable(view,activity,true )
      // display graph
        val populateGraph=  populateGraph()
        populateGraph.CompletePieGraphProgress(view,pieChart)

        return view
    }
}