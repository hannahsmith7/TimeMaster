package com.part.myapplication.ui.tableValues

import android.database.Cursor
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB

class tableValuesSettings {


    fun DisplayAllValuesInTable( view: View, activity: FragmentActivity?,isCompletedNeeded:Boolean) {

        val db = SaveDataSqlLiteDB(activity);
        db.checkData();
        val tableLayout = view.findViewById<TableLayout>(R.id.tl)


        val cursor: Cursor =
            db.getReadableDatabase().rawQuery("SELECT * FROM UserDetails_Table", null)


        if (cursor.moveToFirst()) {
            do {
                //get values from cursor
                val name = cursor.getString(1)
                val date = cursor.getString(2)
                val trackHours = cursor.getString(3)
                val progress = cursor.getString(4)
                val completed = cursor.getString(5)

                //create a new TableRow
                val row = TableRow(activity)
                val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
                row.layoutParams = lp

                //create TextViews for the row
                val textName = TextView(activity)
                textName.text = name
                val textDate = TextView(activity)
                textDate.text = date
                val textTrackHours = TextView(activity)
                textTrackHours.text = trackHours
                val textProgress = TextView(activity)
                textProgress.text = progress
                val textCompleted = TextView(activity)

                textCompleted.text = completed
               // if is completed equals false display completed and uncompleted data in the table isCompletedNeeded
if(!isCompletedNeeded){
    //add TextViews to the row
    row.addView(textName)
    row.addView(textDate)
    row.addView(textTrackHours)
    row.addView(textProgress)

    //if project is completed set background green
    if(completed.equals( "Yes")){
        row.setBackgroundResource(R.color.green)
        row.addView(textCompleted)

    }else{
        row.addView(textCompleted)

    }
}
// if is completed equals true display completed  data in the table isCompletedNeeded
else{
    if(completed.equals( "Yes")){
        row.addView(textName)
        row.addView(textDate)
        row.addView(textTrackHours)
        row.addView(textProgress)
        row.addView(textCompleted)
    }
}



                //add the row to the TableLayout
                tableLayout.addView(row)


            } while (cursor.moveToNext())
        }
    }





}

