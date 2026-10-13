package br.com.travelu.feature.register.domain.usecase

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.register.domain.model.Register
import br.com.travelu.feature.register.domain.repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository,
) {
    suspend operator fun invoke(register: Register): Result<User> = repository.register(register)
}
