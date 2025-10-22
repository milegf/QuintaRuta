package com.example.quintaruta.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quintaruta.data.local.dao.*
import com.example.quintaruta.data.local.entity.*

@Database(
    entities = [UserEntity::class, RutaEntity::class, PoiEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rutaDao(): RutaDao
    abstract fun poiDao(): PoiDao
}
