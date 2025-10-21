package com.example.quintaruta.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
