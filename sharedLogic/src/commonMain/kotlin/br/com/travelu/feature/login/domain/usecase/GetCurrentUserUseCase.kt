package br.com.travelu.feature.login.domain.usecase

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository

class GetCurrentUserUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): Result<User> = repository.getCurrentUser()
}
