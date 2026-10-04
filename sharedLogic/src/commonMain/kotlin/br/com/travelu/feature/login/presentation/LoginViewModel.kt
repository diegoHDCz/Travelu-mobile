package br.com.travelu.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.usecase.GetCurrentUserUseCase
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import br.com.travelu.feature.login.domain.usecase.LogoutUseCase
import br.com.travelu.feature.login.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = loginUseCase(email, password)) {
                is Result.Success -> _uiState.value = LoginUiState(userName = result.data.name)
                is Result.Error -> _uiState.value = LoginUiState(errorMessage = result.message)
            }
        }
    }

    fun onRegisterClicked(
        name: String,
        password: String,
        email: String? = null,
        phone: String? = null,
        username: String? = null,
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = registerUseCase(name, password, email, phone, username)) {
                is Result.Success -> _uiState.value = LoginUiState(userName = result.data.name)
                is Result.Error -> _uiState.value = LoginUiState(errorMessage = result.message)
            }
        }
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getCurrentUserUseCase()) {
                is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, userName = result.data.name)
                is Result.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
            }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = LoginUiState()
        }
    }
}
