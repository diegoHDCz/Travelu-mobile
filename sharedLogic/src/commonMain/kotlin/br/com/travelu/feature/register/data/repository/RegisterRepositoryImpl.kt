package br.com.travelu.feature.register.data.repository

import br.com.travelu.core.network.safeApiCall
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.data.mapper.toDomain
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.register.data.datasource.RegisterRemoteDataSource
import br.com.travelu.feature.register.data.mapper.RegisterRequestMapper
import br.com.travelu.feature.register.domain.model.Register
import br.com.travelu.feature.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    private val remoteDataSource: RegisterRemoteDataSource,
) : RegisterRepository {

    override suspend fun register(register: Register): Result<User> = safeApiCall {
        remoteDataSource.register(RegisterRequestMapper.toDto(register)).toDomain()
    }
}
