package com.part.myapplication.ui.SQLLite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LoginDatabase(context: Context?) : SQLiteOpenHelper(context, "user_db", null, 1) {
    private val C1 = "Username"
    private val C2 = "Password"
    private val C3 = "Image"
    private val C4 = "bool"
    private val TABLE_NAME = "LoginDatabase_Table"
    private val COLUMN_ID = "ID"
    override fun onCreate(DB: SQLiteDatabase) {


        //creating our table
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + C1 + " TEXT, "
                + C2 + " TEXT, "
                + C3 + " BLOB, "
                + C4 + " TEXT);")
        DB.execSQL(query)
    }

    override fun onUpgrade(DB: SQLiteDatabase, i: Int, i1: Int) {
        if (i == 1 && i1 == 2) {
            DB.execSQL("drop Table if exists LoginDatabase_Table")
        }
    }

    fun RetrievedValues(context: Context?): Cursor {
        val obj = LoginDatabase(context)
        val DB = obj.readableDatabase
        return DB.rawQuery("Select * from LoginDatabase_Table", null)
    }
}