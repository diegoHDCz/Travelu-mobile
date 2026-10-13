package br.com.travelu.feature.register.presentation

import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.register.domain.validation.RegisterValidationError

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: RegisterValidationError? = null,
    val confirmPasswordError: RegisterValidationError? = null,
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val canSubmit: Boolean
        get() = !isLoading && name.isNotBlank() && email.isNotBlank() &&
            password.isNotBlank() && confirmPassword.isNotBlank()
}
