package br.com.travelu.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
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

    // Bridge simples para Swift: StateFlow.collect é suspend e o Kotlin/Native
    // não exporta isso de forma direta pro Swift, então expomos um callback comum.
    fun observeState(onChange: (LoginUiState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { onChange(it) }
        }
    }
}

// Holder para plataformas sem ViewModelStoreOwner pronto (iOS puro/SwiftUI sem
// Compose). Ele guarda o LoginViewModel num ViewModelStore próprio e expõe um
// clear() público para o Swift chamar no deinit, disparando o viewModelScope.cancel()
// interno do androidx. No Android/Compose isso não é necessário: use
// androidx.lifecycle.viewmodel.compose.viewModel { LoginViewModel(...) }.
class LoginViewModelHolder(loginUseCase: LoginUseCase) {
    private val store = ViewModelStore()

    val viewModel: LoginViewModel = LoginViewModel(loginUseCase).also { store.put("login", it) }

    fun clear() = store.clear()
}
