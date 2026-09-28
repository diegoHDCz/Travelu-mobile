package br.com.travelu.feature.login.presentation

data class LoginUiState(
    val isLoading: Boolean = false,
    val userName: String? = null,
    val errorMessage: String? = null
)
