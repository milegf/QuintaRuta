package com.example.quintaruta.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quintaruta.db"
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAME = "name"

        // Tabla de Rutas
        const val TABLE_ROUTES = "routes"
        const val COLUMN_ROUTE_ID = "id"
        const val COLUMN_ROUTE_NAME = "name"
        const val COLUMN_ROUTE_DESCRIPTION = "description"
        const val COLUMN_ROUTE_USER_EMAIL = "user_email"

        // Tabla de Puntos de Interés Visitados
        const val TABLE_VISITED_POIS = "visited_pois"
        const val COLUMN_VISITED_POI_ID = "id"
        const val COLUMN_VISITED_POI_USER_EMAIL = "user_email"
        const val COLUMN_VISITED_POI_POI_ID = "poi_id"

        // Tabla de Insignias de Usuario
        const val TABLE_USER_BADGES = "user_badges"
        const val COLUMN_USER_BADGE_ID = "id"
        const val COLUMN_USER_BADGE_USER_EMAIL = "user_email"
        const val COLUMN_USER_BADGE_BADGE_ID = "badge_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
}
