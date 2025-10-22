package com.example.quintaruta.data.repository
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.quintaruta.data.local.DatabaseHelper
import com.example.quintaruta.model.AvailableBadges
import com.example.quintaruta.model.Badge
import com.example.quintaruta.model.Route

class UserRepository(private val context: Context) {

    private val dbHelper = DatabaseHelper(context)

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val KEY_LOGGED_IN = "logged_in"
    private val KEY_EMAIL = "email"
    private val KEY_REMEMBER_ME = "remember_me"

    fun isLoggedIn(): Boolean { return prefs.getBoolean(KEY_LOGGED_IN, false) && prefs.getBoolean(KEY_REMEMBER_ME, false) }

    fun login(email: String, password: String, rememberMe: Boolean): Boolean {
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
                .putBoolean(KEY_REMEMBER_ME, rememberMe)
                .apply()
        } else {
            logout()
        }
        return success
    }

    fun register(email: String, password: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_EMAIL, email)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
            put(DatabaseHelper.COLUMN_NAME, email)
        }
        val newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values)
        db.close()
        return newRowId != -1L
    }

    fun logout() { prefs.edit().clear().apply() }

    fun getLoggedEmail(): String? = prefs.getString(KEY_EMAIL, null)

    @SuppressLint("Range")
    fun getUserName(email: String): String? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_USERS, arrayOf(DatabaseHelper.COLUMN_NAME), "${DatabaseHelper.COLUMN_EMAIL} = ?", arrayOf(email), null, null, null)
        var name: String? = null
        if (cursor.moveToFirst()) { name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)) }
        cursor.close()
        db.close()
        return name
    }

    fun updateUserName(email: String, newName: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply { put(DatabaseHelper.COLUMN_NAME, newName) }
        val rowsAffected = db.update(DatabaseHelper.TABLE_USERS, values, "${DatabaseHelper.COLUMN_EMAIL} = ?", arrayOf(email))
        db.close()
        return rowsAffected
    }

    fun createRoute(name: String, description: String, userEmail: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ROUTE_NAME, name)
            put(DatabaseHelper.COLUMN_ROUTE_DESCRIPTION, description)
            put(DatabaseHelper.COLUMN_ROUTE_USER_EMAIL, userEmail)
        }
        val newRowId = db.insert(DatabaseHelper.TABLE_ROUTES, null, values)
        db.close()
        return newRowId
    }

    @SuppressLint("Range")
    fun getRoutesForUser(userEmail: String): List<Pair<Long, String>> {
        val routes = mutableListOf<Pair<Long, String>>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_ROUTES, arrayOf(DatabaseHelper.COLUMN_ROUTE_ID, DatabaseHelper.COLUMN_ROUTE_NAME), "${DatabaseHelper.COLUMN_ROUTE_USER_EMAIL} = ?", arrayOf(userEmail), null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_ID))
            val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_NAME))
            routes.add(Pair(id, name))
        }
        cursor.close()
        db.close()
        return routes
    }

    @SuppressLint("Range")
    fun getRouteById(routeId: Long): Route? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_ROUTES,
            null,
            "${DatabaseHelper.COLUMN_ROUTE_ID} = ?",
            arrayOf(routeId.toString()),
            null,
            null,
            null
        )
        var route: Route? = null
        if (cursor.moveToFirst()) {
            route = Route(
                id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_ID)),
                name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_NAME)),
                description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_DESCRIPTION)),
                userEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_USER_EMAIL))
            )
        }
        cursor.close()
        db.close()
        return route
    }

    fun updateRoute(route: Route): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ROUTE_NAME, route.name)
            put(DatabaseHelper.COLUMN_ROUTE_DESCRIPTION, route.description)
        }
        val rowsAffected = db.update(
            DatabaseHelper.TABLE_ROUTES,
            values,
            "${DatabaseHelper.COLUMN_ROUTE_ID} = ?",
            arrayOf(route.id.toString())
        )
        db.close()
        return rowsAffected
    }

    fun deleteRoute(routeId: Long): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(DatabaseHelper.TABLE_ROUTES, "${DatabaseHelper.COLUMN_ROUTE_ID} = ?", arrayOf(routeId.toString()))
        db.close()
        return rowsAffected
    }

    // --- FUNCIONES PARA PROGRESO E INSIGNIAS ---

    @SuppressLint("Range")
    fun getVisitedPoisForUser(userEmail: String): Set<Long> {
        val visitedPois = mutableSetOf<Long>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_VISITED_POIS,
            arrayOf(DatabaseHelper.COLUMN_VISITED_POI_POI_ID),
            "${DatabaseHelper.COLUMN_VISITED_POI_USER_EMAIL} = ?",
            arrayOf(userEmail),
            null, null, null
        )
        cursor.use { c ->
            if (c.moveToFirst()) {
                val poiIdIndex = c.getColumnIndex(DatabaseHelper.COLUMN_VISITED_POI_POI_ID)
                if (poiIdIndex != -1) {
                    do {
                        visitedPois.add(c.getLong(poiIdIndex))
                    } while (c.moveToNext())
                }
            }
        }
        db.close()
        return visitedPois
    }

    fun markPoiAsVisited(userEmail: String, poiId: Long) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_VISITED_POI_USER_EMAIL, userEmail)
            put(DatabaseHelper.COLUMN_VISITED_POI_POI_ID, poiId)
        }
        db.insertWithOnConflict(DatabaseHelper.TABLE_VISITED_POIS, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
        checkAndAwardBadges(userEmail)
    }

    fun unmarkPoiAsVisited(userEmail: String, poiId: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.TABLE_VISITED_POIS,
            "${DatabaseHelper.COLUMN_VISITED_POI_USER_EMAIL} = ? AND ${DatabaseHelper.COLUMN_VISITED_POI_POI_ID} = ?",
            arrayOf(userEmail, poiId.toString())
        )
        db.close()
    }

    fun getVisitedPoisCount(userEmail: String): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(DISTINCT ${DatabaseHelper.COLUMN_VISITED_POI_POI_ID}) FROM ${DatabaseHelper.TABLE_VISITED_POIS} WHERE ${DatabaseHelper.COLUMN_VISITED_POI_USER_EMAIL} = ?", arrayOf(userEmail))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    @SuppressLint("Range")
    fun getUnlockedBadges(userEmail: String): List<Badge> {
        val unlockedBadgeIds = mutableSetOf<String>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_USER_BADGES,
            arrayOf(DatabaseHelper.COLUMN_USER_BADGE_BADGE_ID),
            "${DatabaseHelper.COLUMN_USER_BADGE_USER_EMAIL} = ?",
            arrayOf(userEmail),
            null,
            null,
            null
        )

        cursor.use { c ->
            if (c.moveToFirst()) {
                val badgeIdIndex = c.getColumnIndex(DatabaseHelper.COLUMN_USER_BADGE_BADGE_ID)
                if (badgeIdIndex != -1) {
                    do {
                        unlockedBadgeIds.add(c.getString(badgeIdIndex))
                    } while (c.moveToNext())
                }
            }
        }
        db.close()

        return AvailableBadges.all.filter { it.id in unlockedBadgeIds }
    }

    private fun checkAndAwardBadges(userEmail: String) {
        val visitedCount = getVisitedPoisCount(userEmail)
        val currentlyUnlocked = getUnlockedBadges(userEmail).map { it.id }.toSet()
        val db = dbHelper.writableDatabase
        try {
            AvailableBadges.all.forEach { badge ->
                if (visitedCount >= badge.requiredPois && badge.id !in currentlyUnlocked) {
                    val values = ContentValues().apply {
                        put(DatabaseHelper.COLUMN_USER_BADGE_USER_EMAIL, userEmail)
                        put(DatabaseHelper.COLUMN_USER_BADGE_BADGE_ID, badge.id)
                    }
                    db.insert(DatabaseHelper.TABLE_USER_BADGES, null, values)
                }
            }
        } finally {
            db.close()
        }
    }
}