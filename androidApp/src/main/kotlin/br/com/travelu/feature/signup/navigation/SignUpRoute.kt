package br.com.travelu.feature.signup.navigation

import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.travelu.feature.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) =
    navigate(SignUpRoute, navOptions)

fun NavGraphBuilder.signUpScreen(
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    onRegisterSuccess: (email: String) -> Unit,
) {
    composable<SignUpRoute> {
        SignUpScreen(
            onBackClick = dropUnlessResumed(block = onBackClick),
            onSignInClick = dropUnlessResumed(block = onSignInClick),
            onRegisterSuccess = onRegisterSuccess,
        )
    }
}
