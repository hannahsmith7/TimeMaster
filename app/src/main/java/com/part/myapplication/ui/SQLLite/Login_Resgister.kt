package com.part.myapplication.ui.SQLLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Blob

class Login_Resgister   {
    private var isTrue = false
    fun insertValues(username: String?, password: String?,image: ByteArray?,bool: String?, context: Context?): Boolean {
        isTrue = try {
            val obj = LoginDatabase(context)
            val DB = obj.writableDatabase
            val CV = ContentValues()


            CV.put("Username", username)
            CV.put("Password", password)
            CV.put("Image", image) // Store the byte array directly
            CV.put("bool", "false")

            val query = DB.insert("LoginDatabase_Table", null, CV)
            if (query == -1L) {
                false
            } else {
                true
            }
        } catch (e: Exception) {
            false
        }
        return isTrue
    }

    //retrieved method return cursor
    fun RetrievedValues(context: Context?): Cursor {
        val obj = LoginDatabase(context)
        val DB = obj.writableDatabase
        return DB.rawQuery("Select * from LoginDatabase_Table", null)
    }
}