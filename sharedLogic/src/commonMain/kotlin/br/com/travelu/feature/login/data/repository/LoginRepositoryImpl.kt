package br.com.travelu.feature.login.data.repository

import br.com.travelu.core.network.AuthSessionManager
import br.com.travelu.core.network.safeApiCall
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.data.datasource.AuthRemoteDataSource
import br.com.travelu.feature.login.data.mapper.toDomain
import br.com.travelu.feature.login.data.model.LoginRequestDto
import br.com.travelu.feature.login.data.model.LogoutRequestDto
import br.com.travelu.feature.login.data.model.RegisterRequestDto
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val session: AuthSessionManager,
) : LoginRepository {

    override suspend fun register(
        name: String,
        password: String,
        email: String?,
        phone: String?,
        username: String?,
    ): Result<User> = safeApiCall {
        remoteDataSource.register(
            RegisterRequestDto(name = name, password = password, email = email, phone = phone, username = username),
        ).toDomain()
    }

    override suspend fun login(email: String, password: String): Result<User> = safeApiCall {
        val tokens = remoteDataSource.login(LoginRequestDto(login = email, password = password))
        session.saveTokens(tokens.accessToken, tokens.refreshToken)
        remoteDataSource.getCurrentUser(tokens.accessToken).toDomain()
    }

    override suspend fun getCurrentUser(): Result<User> = safeApiCall {
        val accessToken = checkNotNull(session.accessToken) { "Not authenticated" }
        remoteDataSource.getCurrentUser(accessToken).toDomain()
    }

    override suspend fun logout(): Result<Unit> = safeApiCall {
        session.refreshToken?.let { remoteDataSource.logout(LogoutRequestDto(it)) }
        session.clear()
    }
}
