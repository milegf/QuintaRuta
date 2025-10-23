package com.example.quintaruta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.util.UiState
import com.example.quintaruta.util.ValidationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _loginState = MutableLiveData<UiState<Unit>>(UiState.Idle)
    val loginState: LiveData<UiState<Unit>> = _loginState

    fun login(email: String, password: String, rememberMe: Boolean) { // <-- Parámetro añadido
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = UiState.Error("Por favor, completa todos los campos.")
            return
        }
        if (!ValidationUtil.isValidEmail(email)) {
            _loginState.value = UiState.Error("El formato del correo no es válido.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _loginState.postValue(UiState.Loading)
            try {
                val success = userRepository.login(email, password) // Agregar RememberMe
                if (success) {
                    _loginState.postValue(UiState.Success(Unit))
                } else {
                    _loginState.postValue(UiState.Error("Correo o contraseña incorrectos."))
                }
            } catch (e: Exception) {
                _loginState.postValue(UiState.Error("Ocurrió un error inesperado: ${e.message}"))
            }
        }
    }

    fun resetState() {
        _loginState.value = UiState.Idle
    }
}
