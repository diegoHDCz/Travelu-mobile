package br.com.travelu.feature.login.domain.usecase

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository

class RegisterUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(
        name: String,
        password: String,
        email: String? = null,
        phone: String? = null,
        username: String? = null,
    ): Result<User> = repository.register(name, password, email, phone, username)
}
