package com.example.quintaruta.util

import android.util.Patterns

object ValidationUtil {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // For now, we'll just check for a minimum length.
        // This can be expanded with more rules (uppercase, numbers, etc.)
        return password.length >= 6
    }
}
