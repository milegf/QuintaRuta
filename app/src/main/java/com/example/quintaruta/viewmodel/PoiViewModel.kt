package com.example.quintaruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.model.Poi
import com.example.quintaruta.data.repository.PoiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PoiViewModel(private val repository: PoiRepository) : ViewModel() {

    private val _pois = MutableStateFlow<List<Poi>>(emptyList())
    val pois: StateFlow<List<Poi>> = _pois.asStateFlow()

    fun cargarPoisPorRuta(rutaId: Long) {
        viewModelScope.launch {
            repository.getPoisByRuta(rutaId).collect { lista ->
                _pois.value = lista
            }
        }
    }

    fun agregarPoi(poi: Poi) {
        viewModelScope.launch {
            repository.insert(poi)
        }
    }

    fun eliminarPoi(poi: Poi) {
        viewModelScope.launch {
            repository.delete(poi)
        }
    }

    fun actualizarPoi(poi: Poi) {
        viewModelScope.launch {
            repository.update(poi)
        }
    }
}
