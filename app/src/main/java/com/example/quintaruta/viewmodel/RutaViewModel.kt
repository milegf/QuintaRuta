package com.example.quintaruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.model.Ruta
import com.example.quintaruta.data.repository.RutaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RutaViewModel(private val repository: RutaRepository) : ViewModel() {

    val rutas: StateFlow<List<Ruta>> = repository.allRutas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarRuta(ruta: Ruta) {
        viewModelScope.launch {
            repository.insert(ruta)
        }
    }

    fun eliminarRuta(ruta: Ruta) {
        viewModelScope.launch {
            repository.delete(ruta)
        }
    }
}
