package com.part.myapplication.ui.calculations

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.part.myapplication.R
import com.part.myapplication.ui.SQLLite.SaveDataSqlLiteDB

class calculatedValues {

    fun PercentageAchieved(view: View, activity: FragmentActivity?) :Int?{

        val db = SaveDataSqlLiteDB(activity);
        db.checkData();

        val cursor: Cursor =
            db.getReadableDatabase().rawQuery("SELECT * FROM UserDetails_Table", null)
        val sb = java.lang.StringBuilder()
        var progressInt:Int=0
        var index:Int =0


        if (cursor.moveToFirst()) {
            do {

                val progress = cursor.getString(4).removeSuffix("%")
                index++
                var value:Int = progress.toInt()
                progressInt+=value





            } while (cursor.moveToNext())
        }
        var total:Int = (progressInt*100)/(index*100)

        return  total
    }

    @SuppressLint("SetTextI18n")
    fun DashboardCardViewCalculations(view: View, activity: FragmentActivity?, trackedTime:TextView, UpcommingTask:TextView, TotalGoals:TextView, CompletedGoals:TextView) :Int?{

        val db = SaveDataSqlLiteDB(activity);
        db.checkData();
        val cursor: Cursor =
            db.getReadableDatabase().rawQuery("SELECT * FROM UserDetails_Table", null)

        var index:Int =0
        var minutes:Int=0
        var upcomming:Int=0


        if (cursor.moveToFirst()) {
            do {
                val time =cursor.getString(3).removeSuffix("h")
                val progress = cursor.getString(4).removeSuffix("%")
                var sqlMinutes:Int=time.toInt()
                minutes+=sqlMinutes

                if(progress == "100"){

                    upcomming++
                }
                index++






            } while (cursor.moveToNext())
        }
        val total:Double=(upcomming.toDouble()/index.toDouble())
        CompletedGoals.text = ((total*100).toString()+"%")
        trackedTime.text = (minutes*60).toString()+" Minutes"
        TotalGoals.text = (index).toString()+" Project saved"
        if (index> upcomming){
            UpcommingTask.text = (index-upcomming).toString()+" Tasks due"


        }
        else{
            UpcommingTask.text = (upcomming-index).toString()+" Tasks due"

        }


        val isTrue:Boolean =false
       //i will add some code hear later to check for error

        return 0
    }

}