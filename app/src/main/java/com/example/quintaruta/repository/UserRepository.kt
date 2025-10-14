package com.example.quintaruta.repository

import android.content.ContentValues
import android.content.Context
import com.example.quintaruta.DatabaseHelper

class UserRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun login(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_USERS} WHERE ${DatabaseHelper.COLUMN_EMAIL} = ? AND ${DatabaseHelper.COLUMN_PASSWORD} = ?", arrayOf(email, password))
        val success = cursor.moveToFirst()
        cursor.close()
        db.close()
        return success
    }

    fun register(email: String, password: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_EMAIL, email)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
        }
        val newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values)
        db.close()
        return newRowId != -1L
    }
}
