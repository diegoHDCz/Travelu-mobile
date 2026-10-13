package br.com.travelu.feature.login.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.travelu.feature.login.ui.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

// Result key set by sign up on the login back stack entry before popping back.
const val REGISTERED_EMAIL_KEY = "registered_email"

fun NavGraphBuilder.loginScreen(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    composable<LoginRoute> { backStackEntry ->
        val savedStateHandle = backStackEntry.savedStateHandle
        val registeredEmail by savedStateHandle
            .getStateFlow<String?>(REGISTERED_EMAIL_KEY, null)
            .collectAsStateWithLifecycle()

        LoginScreen(
            registeredEmail = registeredEmail,
            onRegisteredEmailConsumed = { savedStateHandle.remove<String>(REGISTERED_EMAIL_KEY) },
            onSignUpClick = dropUnlessResumed(block = onSignUpClick),
            onLoginSuccess = onLoginSuccess,
        )
    }
}