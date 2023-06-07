package com.part.myapplication.ui.Graph

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB

class populateGraph {

    fun populateTrackTime(view: View, lineChart: LineChart) {


        val data = SaveDataSqlLiteDB(view.context).readData()

        val xAxisValues = ArrayList<String>()
        val dataSet1Values = ArrayList<Entry>()
        val dataSet2Values = ArrayList<Entry>()
        val dataSet3Values = ArrayList<Entry>()

        for (i in data.indices) {
            val entry = data[i]
            xAxisValues.add(entry.name) // Add name to X-axis

            val progress = entry.progress.replace("%", "") // Remove percentage symbol
            dataSet1Values.add(Entry(i.toFloat(), progress.toFloat())) // Add progress to dataset 1

            val trackHours = entry.trackHours
            val numericTrackHours = trackHours.filter { it.isDigit() }
                .toFloat() // Extract numeric part and convert to float
            dataSet2Values.add(Entry(i.toFloat(), numericTrackHours)) // Add trackHours to dataset 2

            val completedValue = if (entry.completed == "Yes") 1f else 0f
            dataSet3Values.add(Entry(i.toFloat(), completedValue)) // Add completed to dataset 3
        }

        val dataSet1 = LineDataSet(dataSet1Values, "Progress")
        dataSet1.color = Color.RED // Set color for dataset 1

        val dataSet2 = LineDataSet(dataSet2Values, "Track Hours")
        dataSet2.color = Color.BLUE // Set color for dataset 2

        val dataSet3 = LineDataSet(dataSet3Values, "Completed")
        dataSet3.color = Color.GREEN // Set color for dataset 3

        val lineData = LineData(dataSet1, dataSet2, dataSet3)

        lineChart.data = lineData
        lineChart.invalidate() // Refresh the chart
    }

    fun Pie_graph_TrackTime(view: View, pieChart: PieChart) {


        val data = SaveDataSqlLiteDB(view.context).readData()

        val pieEntries = ArrayList<PieEntry>()

        for (i in data.indices) {
            val entry = data[i]
            val progress = entry.progress.replace("%", "")
                .toFloat() // Remove percentage symbol and convert to float
            pieEntries.add(PieEntry(progress, entry.name))
        }

        val dataSet = PieDataSet(pieEntries, "Progress")
        dataSet.colors =
            mutableListOf(Color.RED, Color.BLUE, Color.GREEN) // Set colors for the pie slices

        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }


    fun upcommingLineGraph(view: View, lineChart: LineChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()
        val dataSetValues = ArrayList<Entry>()

        for (i in data.indices) {
            val entry = data[i]
            val progress = entry.progress.replace("%", "")
            dataSetValues.add(Entry(i.toFloat(), progress.toFloat()))
        }

        val dataSet = LineDataSet(dataSetValues, "Progress")
        dataSet.color = Color.RED // Set color for the dataset

        val lineData = LineData(dataSet)

        lineChart.data = lineData
        lineChart.invalidate() // Refresh the chart
    }


    fun upcommingPieGraph(view: View, pieChart: PieChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()

        val pieEntries = ArrayList<PieEntry>()
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            pieEntries.add(PieEntry(progress, entry.name))
        }

        val dataSet = PieDataSet(pieEntries, "Progress")
        dataSet.sliceSpace = 3f
        dataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.CYAN)

        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }

    fun GoalsPieGraph(view: View, pieChart: PieChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()


        val pieEntries = ArrayList<PieEntry>()
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            if (progress < 100) {
                pieEntries.add(PieEntry(progress, entry.name))
            }
        }

        val dataSet = PieDataSet(pieEntries, "Progress")
        dataSet.sliceSpace = 3f
        dataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.CYAN)

        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }


    fun GoalsLineGraph(view: View, lineChart: LineChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()

        val lineEntries = ArrayList<Entry>()
        var index = 0f
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            if (progress < 100) {
                lineEntries.add(Entry(index, progress))
                index++
            }
        }

        val lineDataSet = LineDataSet(lineEntries, "Progress")
        lineDataSet.color = Color.BLUE
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawValues(true)

        val lineData = LineData(lineDataSet)

        lineChart.data = lineData
        lineChart.invalidate() // Refresh the chart
    }


    fun CompleteLineGraph(view: View, lineChart: LineChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()

        val lineEntries = ArrayList<Entry>()
        var index = 0f
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            val completed = entry.completed.toLowerCase()
            if (progress >99 && completed == "yes") {
                lineEntries.add(Entry(index, progress))
                index++
            }
        }

        val lineDataSet = LineDataSet(lineEntries, "Progress")
        lineDataSet.color = Color.BLUE
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawValues(true)

        val lineData = LineData(lineDataSet)

        lineChart.data = lineData
        lineChart.invalidate() // Refresh the chart
    }


    fun CompletePieGraph(view: View, pieChart: PieChart) {


        val data = SaveDataSqlLiteDB(view.context).readData()


        val pieEntries = ArrayList<PieEntry>()
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            if (progress > 99) {
                pieEntries.add(PieEntry(progress, entry.name))
            }
        }
        val dataSet = PieDataSet(pieEntries, "Progress")
        dataSet.color = Color.BLUE

        dataSet.setDrawValues(true)


        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate()
        pieChart.apply {

        }
    }


    fun CompletePieGraphProgress(view: View, pieChart: PieChart) {
        val data = SaveDataSqlLiteDB(view.context).readData()


        val pieEntries = ArrayList<PieEntry>()
        for (entry in data) {
            val progress = entry.progress.replace("%", "").toFloat()
            if (progress > 99) {
                pieEntries.add(PieEntry(progress, entry.name))
            }
        }
        val dataSet = PieDataSet(pieEntries, "Progress")
        dataSet.color = Color.BLUE

        dataSet.setDrawValues(true)


        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate()
        pieChart.apply {
        }
    }
}
