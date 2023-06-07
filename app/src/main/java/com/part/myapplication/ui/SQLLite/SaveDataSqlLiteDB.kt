package com.part.myapplication.ui.SQLLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.part.myapplication.ui.util.UserDetails
import com.part.myapplication.ui.util.getAndSet


class SaveDataSqlLiteDB (context: Context?) : SQLiteOpenHelper(context, "values_db", null, 1) {
    private val TABLE_NAME = "UserDetails_Table"
    private val C0 = "ID"
    private val C1 = "Name"
    private val C2 = "Date"
    private val C3 = "TrackHours"
    private val C4 = "Progress"
    private val C5 = "Completed"

    override fun onCreate(DB: SQLiteDatabase) {


        //creating our table
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " ( " + C0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C1 + " TEXT, "
                + C2 + " TEXT, "
                + C3 + " TEXT, "
                + C4 + " TEXT, "
                + C5 + " TEXT " + ");")
        DB.execSQL(query)
    }

    override fun onUpgrade(DB: SQLiteDatabase, i: Int, i1: Int) {
        if (i == 1 && i1 == 2) {
            DB.execSQL("drop Table if exists UserDetails_Table")
        }
    }

    fun insertData( name:String, date:String, trackhours:String, progress:String, completed:String){
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(C1,name)
        cv.put(C2,date)
        cv.put(C3,trackhours)
        cv.put(C4,progress)
        cv.put(C5, completed)
        db.insert(TABLE_NAME,null,cv)
        db.close()

    }

    fun checkData(){
        val db =this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query,null)
        if(result.count == 0){
            insertData( "Opsd part 1", "10/04/2023", "14h", "100%", "Yes")
            insertData( "prog part 1", "03/05/2023", "10h", "50%", "no")
            insertData( "Opsd poe", "10/07/2023", "20h", "0%", "no")
            insertData( "Inrs part 2", "20/07/2023", "10h", "60%", "no")
            insertData( "Opsd part 2", "10/06/2023", "10h", "100%", "Yes")
        }
        result.close()
        db.close()
    }


    fun readData(context: Context?){
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query,null)

        if(result.count == 0){

            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show()
        }else{
            while (result.moveToNext()){
                val name = result.getString(1)
                val date = result.getString(2)
                val trackhours = result.getString(3)
                val progress = result.getString(4)
                val completed = result.getString(5)


            }
        }
        result.close()
        db.close()

    }


    @SuppressLint("Range")
    fun readData(): List<getAndSet> {
        val dataList = ArrayList<getAndSet>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val name = result.getString(result.getColumnIndex(C1))
                val progress = result.getString(result.getColumnIndex(C4))
                val trackHours = result.getString(result.getColumnIndex(C3))

                val entry = getAndSet()
                entry.name = name
                entry.progress = progress
                entry.trackHours = trackHours

                dataList.add(entry)
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return dataList
    }
    @SuppressLint("Range")
    fun retrieveNames(): List<String> {
        val names = ArrayList<String>()
        val db = this.readableDatabase
        val query = "SELECT DISTINCT $C1 FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val name = result.getString(result.getColumnIndex(C1))
                names.add(name)
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return names
    }

    fun updateDetails(name: String, date: String, trackHours: String, progress: String, completed: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(C2, date)
        cv.put(C3, trackHours)
        cv.put(C4, progress)
        cv.put(C5, completed)

        val whereClause = "$C1 = ?"
        val whereArgs = arrayOf(name)

        db.update(TABLE_NAME, cv, whereClause, whereArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun getUserDetailsByName(name: String): UserDetails? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $C1 = ?"
        val selectionArgs = arrayOf(name)
        val result = db.rawQuery(query, selectionArgs)

        var userDetails: UserDetails? = null

        if (result.moveToFirst()) {
            val id = result.getInt(result.getColumnIndex(C0))
            val date = result.getString(result.getColumnIndex(C2))
            val trackHours = result.getString(result.getColumnIndex(C3))
            val progress = result.getString(result.getColumnIndex(C4))
            val completed = result.getString(result.getColumnIndex(C5))

            userDetails = UserDetails(id, name, date, trackHours, progress, completed)
        }

        result.close()
        db.close()

        return userDetails
    }
}