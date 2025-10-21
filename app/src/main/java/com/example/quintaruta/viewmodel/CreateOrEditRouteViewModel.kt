package com.example.quintaruta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.model.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateOrEditRouteUiState(
    val routeId: Long? = null,
    val name: String = "",
    val description: String = "",
    val isSaving: Boolean = false,
    val isRouteLoaded: Boolean = false
)

class CreateOrEditRouteViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(CreateOrEditRouteUiState())
    val uiState = _uiState.asStateFlow()

    fun loadRoute(routeId: Long) {
        if (routeId == 0L) {
            _uiState.update { it.copy(isRouteLoaded = true) } // Prepara para nueva ruta
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val route = userRepository.getRouteById(routeId)
            if (route != null) {
                _uiState.update {
                    it.copy(
                        routeId = route.id,
                        name = route.name,
                        description = route.description,
                        isRouteLoaded = true
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun saveRoute(onSaved: (Long) -> Unit) {
        _uiState.update { it.copy(isSaving = true) }

        viewModelScope.launch(Dispatchers.IO) {
            val userEmail = userRepository.getLoggedEmail()
            if (userEmail == null) {
                _uiState.update { it.copy(isSaving = false) }
                return@launch
            }

            val currentState = _uiState.value
            val savedRouteId: Long

            if (currentState.routeId == null || currentState.routeId == 0L) {
                // Creación de una nueva ruta
                savedRouteId = userRepository.createRoute(currentState.name, currentState.description, userEmail)
            } else {
                // Actualización de una ruta existente
                val routeToUpdate = Route(
                    // Usamos !! porque en este bloque sabemos que routeId no es nulo
                    id = currentState.routeId!!,
                    name = currentState.name,
                    description = currentState.description,
                    userEmail = userEmail
                )
                userRepository.updateRoute(routeToUpdate)
                savedRouteId = routeToUpdate.id
            }

            launch(Dispatchers.Main) {
                onSaved(savedRouteId)
            }
        }
    }
}