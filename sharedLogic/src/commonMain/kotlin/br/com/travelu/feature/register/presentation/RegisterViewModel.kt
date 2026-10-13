package br.com.travelu.feature.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.travelu.core.util.Result
import br.com.travelu.feature.register.domain.model.Register
import br.com.travelu.feature.register.domain.usecase.RegisterUseCase
import br.com.travelu.feature.register.domain.validation.RegisterValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null)
    }

    fun onPhoneChanged(phone: String) {
        _uiState.value = _uiState.value.copy(phone = phone)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password, confirmPasswordError = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword, confirmPasswordError = null)
    }

    fun onRegisterClicked() {
        val state = _uiState.value
        val emailError = RegisterValidator.validateEmail(state.email)
        val confirmPasswordError = RegisterValidator.validatePasswordConfirmation(state.password, state.confirmPassword)
        if (emailError != null || confirmPasswordError != null) {
            _uiState.value = state.copy(emailError = emailError, confirmPasswordError = confirmPasswordError)
            return
        }
        val register = Register(
            name = state.name,
            password = state.password,
            email = state.email.trim(),
            phone = state.phone.ifBlank { null },
        )
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = registerUseCase(register)) {
                is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, user = result.data)
                is Result.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
            }
        }
    }
}
