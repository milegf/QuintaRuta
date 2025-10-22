package com.example.quintaruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.repository.TriviaRepository
import com.example.quintaruta.data.model.Trivia
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TriviaViewModel(
    private val repository: TriviaRepository
) : ViewModel() {

    private val _trivias = MutableStateFlow<List<Trivia>>(emptyList())
    val trivias: StateFlow<List<Trivia>> = _trivias.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun loadTriviasByPoi(poiId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTriviasByPoi(poiId).collect { lista ->
                _trivias.value = lista
                _isLoading.value = false
            }
        }
    }

    fun insertTrivia(trivia: Trivia) {
        viewModelScope.launch {
            try {
                repository.insertTrivia(trivia)
                _message.value = "Trivia agregada correctamente"
            } catch (e: Exception) {
                _message.value = "Error al agregar trivia"
            }
        }
    }

    fun marcarTriviaRespondida(trivia: Trivia) {
        viewModelScope.launch {
            try {
                repository.updateTrivia(trivia.copy(respondida = true))
                _message.value = "¡Trivia completada!"
            } catch (e: Exception) {
                _message.value = "Error al actualizar trivia"
            }
        }
    }

    fun resetTrivias() {
        viewModelScope.launch {
            repository.deleteAll()
            _trivias.value = emptyList()
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}