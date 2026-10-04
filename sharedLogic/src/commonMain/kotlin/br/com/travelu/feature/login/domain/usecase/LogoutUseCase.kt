package br.com.travelu.feature.login.domain.usecase

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.repository.LoginRepository

class LogoutUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): Result<Unit> = repository.logout()
}
