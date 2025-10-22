package com.example.quintaruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.model.Ruta
import com.example.quintaruta.data.repository.RutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(private val repository: RutaRepository) : ViewModel() {

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> = _rutas.asStateFlow()

    init {
        cargarRutas()
    }

    fun cargarRutas() {
        viewModelScope.launch {
            repository.allRutas.collect { lista ->
                _rutas.value = lista
            }
        }
    }

    fun refrescarRutas() {
        cargarRutas()
    }
}
