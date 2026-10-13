package br.com.travelu.feature.login.presentation

import br.com.travelu.feature.login.domain.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
