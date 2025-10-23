package com.example.quintaruta.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quintaruta.data.local.dao.*
import com.example.quintaruta.data.local.entity.*

@Database(
    entities = [UserEntity::class,
                RutaEntity::class,
                PoiEntity::class,
                TriviaEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rutaDao(): RutaDao
    abstract fun poiDao(): PoiDao
    abstract fun triviaDao(): TriviaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quintaruta_db"
                )

                    // .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
