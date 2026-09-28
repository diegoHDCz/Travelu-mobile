package br.com.travelu.feature.login.data.repository

import br.com.travelu.core.util.Result
import br.com.travelu.feature.login.domain.model.User
import br.com.travelu.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.delay

// Implementação de exemplo (sem rede/banco ainda).
// Quando entrar Ktor/SQLDelight, isso passa a chamar remote/ e local/ e virar a fonte real.
class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        delay(500)
        return Result.Success(User(id = "1", name = email.substringBefore("@")))
    }
}
