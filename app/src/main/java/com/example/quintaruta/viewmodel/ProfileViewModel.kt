package com.example.quintaruta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.model.Badge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val profileImageUrl: String? = null,
    val userRoutes: List<Pair<Long, String>> = emptyList(),
    val visitedPoisCount: Int = 0,
    val unlockedBadges: List<Badge> = emptyList()
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val loggedInEmail = userRepository.getLoggedEmail()
            if (loggedInEmail == null) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val userName = userRepository.getUserName(loggedInEmail) ?: loggedInEmail
            val routes = userRepository.getRoutesForUser(loggedInEmail)
            val visitedCount = userRepository.getVisitedPoisCount(loggedInEmail)
            val badges = userRepository.getUnlockedBadges(loggedInEmail)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    userName = userName,
                    userEmail = loggedInEmail,
                    userRoutes = routes,
                    visitedPoisCount = visitedCount,
                    unlockedBadges = badges
                )
            }
        }
    }

    fun updateUserName(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentEmail = _uiState.value.userEmail
            if (currentEmail.isNotBlank() && newName.isNotBlank()) {
                userRepository.updateUserName(currentEmail, newName)
                loadProfileData()
            }
        }
    }

    fun deleteRoute(routeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteRoute(routeId)
            loadProfileData()
        }
    }
}
