package br.com.travelu.feature.login.data.repository

import br.com.travelu.core.network.AuthSessionManager
import br.com.travelu.core.network.safeApiCall
import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.data.datasource.AuthRemoteDataSource
import br.com.travelu.feature.login.data.mapper.toDomain
import br.com.travelu.feature.login.data.model.LoginRequestDto
import br.com.travelu.feature.login.data.model.LogoutRequestDto
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val session: AuthSessionManager,
) : LoginRepository {

    override fun hasSession(): Boolean = session.isAuthenticated

    override suspend fun login(email: String, password: String): Result<User> = safeApiCall {
        val tokens = remoteDataSource.login(LoginRequestDto(login = email, password = password))
        session.saveTokens(tokens.accessToken, tokens.refreshToken)
        remoteDataSource.invalidateCachedTokens()
        remoteDataSource.getCurrentUser().toDomain()
    }

    override suspend fun getCurrentUser(): Result<User> = safeApiCall {
        check(session.isAuthenticated) { "Not authenticated" }
        remoteDataSource.getCurrentUser().toDomain()
    }

    override suspend fun logout(): Result<Unit> = safeApiCall {
        try {
            session.refreshToken?.let { remoteDataSource.logout(LogoutRequestDto(it)) }
        } finally {
            // Mesmo se a revogação no servidor falhar, o aparelho esquece a sessão.
            session.clear()
            remoteDataSource.invalidateCachedTokens()
        }
    }
}
