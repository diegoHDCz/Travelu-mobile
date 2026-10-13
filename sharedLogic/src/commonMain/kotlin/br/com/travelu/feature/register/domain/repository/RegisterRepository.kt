package br.com.travelu.feature.register.domain.repository

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.register.domain.model.Register

interface RegisterRepository {
    suspend fun register(register: Register): Result<User>
}
