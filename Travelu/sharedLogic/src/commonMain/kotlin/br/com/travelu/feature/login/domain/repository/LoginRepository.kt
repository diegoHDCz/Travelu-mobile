package br.com.travelu.feature.login.domain.repository

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<User>
}
