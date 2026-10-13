package br.com.travelu.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.usecase.GetCurrentUserUseCase
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import br.com.travelu.feature.login.domain.usecase.LogoutUseCase
import br.com.travelu.feature.login.domain.usecase.RestoreSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val restoreSessionUseCase: RestoreSessionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        restoreSession()
    }

    // Abertura do app: se há tokens salvos, busca o usuário atual (o plugin Auth
    // renova o access token se preciso). Falha aqui não vira mensagem de erro —
    // o usuário só vê o formulário de login.
    private fun restoreSession() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRestoringSession = true)
            val user = (restoreSessionUseCase() as? Result.Success)?.data
            _uiState.value = _uiState.value.copy(isRestoringSession = false, user = user)
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClicked() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = loginUseCase(email, password)) {
                is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, user = result.data)
                is Result.Error -> _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
            }
        }
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getCurrentUserUseCase()) {
                is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, user = result.data)
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
