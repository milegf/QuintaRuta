package com.example.quintaruta

import android.app.Application
import androidx.room.Room
import com.example.quintaruta.data.local.AppDatabase

class QuintaRutaApp : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "quintaruta_db"
        ).build()
    }
}
