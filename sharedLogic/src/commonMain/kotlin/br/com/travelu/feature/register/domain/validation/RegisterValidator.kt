package br.com.travelu.feature.register.domain.validation

enum class RegisterValidationError {
    INVALID_EMAIL,
    PASSWORDS_DO_NOT_MATCH,
}

// Regras puras (sem Android/iOS), compartilhadas pelas duas plataformas.
object RegisterValidator {

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validateEmail(email: String): RegisterValidationError? =
        if (emailRegex.matches(email.trim())) null else RegisterValidationError.INVALID_EMAIL

    fun validatePasswordConfirmation(password: String, confirmPassword: String): RegisterValidationError? =
        if (password == confirmPassword) null else RegisterValidationError.PASSWORDS_DO_NOT_MATCH
}
