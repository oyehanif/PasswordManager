package com.hanif.passwordmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanif.passwordmanager.model.PasswordRepository
import com.hanif.passwordmanager.model.local.PasswordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordManagerViewModel @Inject constructor(
    private val repository: PasswordRepository
) : ViewModel() {

    // State for password items
    private val _passwordItems = MutableStateFlow<List<PasswordEntity>>(emptyList())
    val passwordItems: StateFlow<List<PasswordEntity>> = _passwordItems.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error state using sealed class
    sealed class UiState {
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Success)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPasswords()
    }

    private fun loadPasswords() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val passwords = repository.getAllPasswords().filterNotNull()
                _passwordItems.value = passwords
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load passwords: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPasswordEntity(item: PasswordEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.savePassword(item)
                _passwordItems.value = _passwordItems.value + item
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to add password: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePasswordEntity(item: PasswordEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updatePassword(item)
                _passwordItems.value = _passwordItems.value.map { if (it.id == item.id) item else it }
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to update password: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePasswordEntity(itemId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deletePassword(itemId)
                _passwordItems.value = _passwordItems.value.filter { it.id != itemId }
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to delete password: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun getPasswordEntityById(itemId: String): PasswordEntity? {
        return repository.getPasswordById(itemId)
    }
}