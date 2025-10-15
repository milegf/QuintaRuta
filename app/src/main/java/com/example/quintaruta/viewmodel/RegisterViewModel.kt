package com.example.quintaruta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.utils.UiState
import com.example.quintaruta.utils.ValidationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _registerState = MutableLiveData<UiState<Unit>>(UiState.Idle)
    val registerState: LiveData<UiState<Unit>> = _registerState

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _registerState.value = UiState.Error("Por favor, completa todos los campos.")
            return
        }
        if (!ValidationUtil.isValidEmail(email)) {
            _registerState.value = UiState.Error("El formato del correo no es válido.")
            return
        }
        if (!ValidationUtil.isValidPassword(password)) {
            _registerState.value = UiState.Error("La contraseña debe tener al menos 6 caracteres.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _registerState.postValue(UiState.Loading)
            try {
                val success = userRepository.register(email, password)
                if (success) {
                    _registerState.postValue(UiState.Success(Unit))
                } else {
                    _registerState.postValue(UiState.Error("No se pudo completar el registro."))
                }
            } catch (e: Exception) {
                _registerState.postValue(UiState.Error("Ocurrió un error inesperado: ${e.message}"))
            }
        }
    }

    fun resetState() {
        _registerState.value = UiState.Idle
    }
}
