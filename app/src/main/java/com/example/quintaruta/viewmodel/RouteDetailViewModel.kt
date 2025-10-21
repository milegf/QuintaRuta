package com.example.quintaruta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.MockData
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.model.PointOfInterest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RouteDetailUiState(
    val pois: List<PointOfInterest> = emptyList(),
    val visitedPoiIds: Set<Long> = emptySet()
)

class RouteDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(RouteDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val userRepository = UserRepository(application)

    fun loadPoisForRoute(routeId: Long) {
        // Usamos datos de prueba (mock)
        val poisForRoute = MockData.pois.filter { it.routeId == routeId }
        _uiState.update { it.copy(pois = poisForRoute) }
        // Cargar los POIs reales visitados
        loadVisitedPois()
    }

    private fun loadVisitedPois() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getLoggedEmail()?.let { userEmail ->
                // Aquí sí leemos de la base de datos para obtener los IDs visitados
                val visitedIds = userRepository.getVisitedPoisForUser(userEmail)
                _uiState.update { it.copy(visitedPoiIds = visitedIds) }
            }
        }
    }

    fun togglePoiVisitedState(poiId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userEmail = userRepository.getLoggedEmail() ?: return@launch
            val isCurrentlyVisited = _uiState.value.visitedPoiIds.contains(poiId)

            if (isCurrentlyVisited) {
                userRepository.unmarkPoiAsVisited(userEmail, poiId)
            } else {
                userRepository.markPoiAsVisited(userEmail, poiId)
            }

            // Recargamos el estado de los POIs visitados para que la UI se actualice
            loadVisitedPois()
        }
    }
}
// Necesitaremos añadir `getVisitedPoisForUser` a UserRepository
