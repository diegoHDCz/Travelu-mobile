package br.com.travelu.feature.login.presentation

import br.com.travelu.feature.login.domain.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val user: User? = null,
    val isLoading: Boolean = false,
    // Validando tokens salvos na abertura do app; a UI mostra um loading em vez do formulário.
    val isRestoringSession: Boolean = false,
    val errorMessage: String? = null
)
