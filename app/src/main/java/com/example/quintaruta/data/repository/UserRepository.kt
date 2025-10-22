package com.example.quintaruta.data.repository

import android.content.Context
import androidx.room.Room
import com.example.quintaruta.data.local.AppDatabase
import com.example.quintaruta.data.local.entity.UserEntity

class UserRepository(private val context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "quintaruta.db"
    ).build()

    private val userDao = db.userDao()

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val KEY_LOGGED_IN = "logged_in"
    private val KEY_EMAIL = "email"

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        val success = user != null

        if (success) {
            prefs.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putString(KEY_EMAIL, email)
                .apply()
        }
        return success
    }

    suspend fun register(email: String, password: String): Boolean {
        val userEntity = UserEntity(email = email, password = password)
        userDao.insert(userEntity)
        return true
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .remove(KEY_EMAIL)
            .apply()
    }

    fun getLoggedEmail(): String? = prefs.getString(KEY_EMAIL, null)
}
