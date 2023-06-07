package com.part.myapplication.ui.Fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB
import com.part.myapplication.ui.calculations.calculatedValues
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.part.myapplication.ui.Graph.populateGraph


class DashBoardFragment : Fragment() {
    @SuppressLint("MissingInflatedId")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val objCalculatedValued = calculatedValues()
        val txtTracktime = view.findViewById<TextView>(R.id.txtTracktime)
        val txtUpcomming = view.findViewById<TextView>(R.id.txtUpcomming)
        val txtTotalgoals = view.findViewById<TextView>(R.id.txtTotalgoals)
        val txtCompleteGoal = view.findViewById<TextView>(R.id.txtCompleteGoal)
        val lnTrackTime = view.findViewById<LinearLayout>(R.id.lnTrackTime)
        val lnCompleted = view.findViewById<LinearLayout>(R.id.lnCompleted)
        val lnUpcommingTask = view.findViewById<LinearLayout>(R.id.lnUpcommingTask)
        val lnGoals = view.findViewById<LinearLayout>(R.id.lnGoals)
        val lineChart = view.findViewById<LineChart>(R.id.chart)
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        val populateGraph=  populateGraph()
        if (objCalculatedValued.DashboardCardViewCalculations(
                view,
                activity,
                txtTracktime,
                txtUpcomming,
                txtTotalgoals,
                txtCompleteGoal
            ) == 0
        ) {
        }
        lnTrackTime.setBackgroundColor(Color.RED)
        populateGraph.populateTrackTime(view,lineChart)
        populateGraph.Pie_graph_TrackTime(view,pieChart)

        lnTrackTime.setOnClickListener(){
            // Create and display the line graph
            lnTrackTime.setBackgroundColor(Color.RED)
            lnUpcommingTask.setBackgroundColor(Color.WHITE)
            lnGoals.setBackgroundColor(Color.WHITE)
            lnCompleted.setBackgroundColor(Color.WHITE)

            populateGraph.populateTrackTime(view,lineChart)
            populateGraph.Pie_graph_TrackTime(view,pieChart)
        }

        lnUpcommingTask.setOnClickListener(){
            lnTrackTime.setBackgroundColor(Color.WHITE)
            lnUpcommingTask.setBackgroundColor(Color.RED)
            lnGoals.setBackgroundColor(Color.WHITE)
            lnCompleted.setBackgroundColor(Color.WHITE)

            populateGraph.upcommingLineGraph(view,lineChart)
            populateGraph.upcommingPieGraph(view,pieChart)

        }

        lnGoals.setOnClickListener(){
            lnTrackTime.setBackgroundColor(Color.WHITE)
            lnUpcommingTask.setBackgroundColor(Color.WHITE)
            lnGoals.setBackgroundColor(Color.RED)
            lnCompleted.setBackgroundColor(Color.WHITE)
            populateGraph.GoalsLineGraph(view,lineChart)
            populateGraph.GoalsPieGraph(view,pieChart)

        }
        lnCompleted.setOnClickListener(){
            lnTrackTime.setBackgroundColor(Color.WHITE)
            lnUpcommingTask.setBackgroundColor(Color.WHITE)
            lnGoals.setBackgroundColor(Color.WHITE)
            lnCompleted.setBackgroundColor(Color.RED)
            populateGraph.CompleteLineGraph(view,lineChart)
            populateGraph.CompletePieGraph(view,pieChart)

        }

        return view
    }

}