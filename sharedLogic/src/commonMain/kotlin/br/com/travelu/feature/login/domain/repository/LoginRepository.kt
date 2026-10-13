package br.com.travelu.feature.login.domain.repository

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User

interface LoginRepository {
    // Há tokens salvos de um login anterior (ainda não validados no servidor).
    fun hasSession(): Boolean

    suspend fun login(email: String, password: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun logout(): Result<Unit>
}
