package com.example.quintaruta.util

import android.util.Patterns

object ValidationUtil {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // TODO: FALTAN IMPLEMENTAR MÁS METODOS DE VALIDACIONES
        return password.length >= 6
    }
}
