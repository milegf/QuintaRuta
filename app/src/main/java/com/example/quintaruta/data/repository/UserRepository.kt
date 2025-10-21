package com.example.quintaruta.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.quintaruta.data.local.DatabaseHelper

class UserRepository(private val context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // Preferencias para estado de sesión
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val KEY_LOGGED_IN = "logged_in"
    private val KEY_EMAIL = "email"

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun login(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_USERS} " +
                    "WHERE ${DatabaseHelper.COLUMN_EMAIL} = ? " +
                    "AND ${DatabaseHelper.COLUMN_PASSWORD} = ?",
            arrayOf(email, password)
        )

        val success = cursor.moveToFirst()
        cursor.close()
        db.close()

        if (success) {
            prefs.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putString(KEY_EMAIL, email)
                .apply()
        }
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

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .remove(KEY_EMAIL)
            .apply()
    }

    fun getLoggedEmail(): String? = prefs.getString(KEY_EMAIL, null)

}
