package br.com.travelu.feature.login.domain.repository

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User

interface LoginRepository {
     suspend fun register(
        name: String,
        password: String,
        email: String? = null,
        phone: String? = null,
        username: String? = null,
    ): Result<User>

    suspend fun login(email: String, password: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun logout(): Result<Unit>
}
