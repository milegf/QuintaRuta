package com.example.quintaruta.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quintaruta.db"
        private const val DATABASE_VERSION = 4 // Versión actualizada

        // Tabla de Usuarios
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
        // Crear TODAS las tablas desde el principio
        val createUserTable = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EMAIL TEXT UNIQUE, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_NAME TEXT)"
        db.execSQL(createUserTable)

        val createRouteTable = "CREATE TABLE $TABLE_ROUTES (" +
                "$COLUMN_ROUTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_ROUTE_NAME TEXT, " +
                "$COLUMN_ROUTE_DESCRIPTION TEXT, " +
                "$COLUMN_ROUTE_USER_EMAIL TEXT)"
        db.execSQL(createRouteTable)

        val createVisitedPoisTable = "CREATE TABLE $TABLE_VISITED_POIS (" +
                "$COLUMN_VISITED_POI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_VISITED_POI_USER_EMAIL TEXT, " +
                "$COLUMN_VISITED_POI_POI_ID INTEGER)"
        db.execSQL(createVisitedPoisTable)

        val createUserBadgesTable = "CREATE TABLE $TABLE_USER_BADGES (" +
                "$COLUMN_USER_BADGE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_BADGE_USER_EMAIL TEXT, " +
                "$COLUMN_USER_BADGE_BADGE_ID TEXT)"
        db.execSQL(createUserBadgesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En una app real, aquí se migrarían los datos.
        // Para este proyecto, simplemente eliminamos las tablas y las volvemos a crear.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROUTES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VISITED_POIS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_BADGES")
        onCreate(db)
    }
}
