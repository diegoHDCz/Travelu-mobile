package br.com.travelu.feature.login.domain.usecase

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository

// Usado na abertura do app: valida os tokens salvos buscando o usuário atual.
// Retorna null quando não há sessão salva, sem tocar na rede.
class RestoreSessionUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): Result<User>? {
        if (!repository.hasSession()) return null
        return repository.getCurrentUser()
    }
}
