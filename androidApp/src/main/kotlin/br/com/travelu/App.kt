package br.com.travelu

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.travelu.feature.login.navigation.LoginRoute
import br.com.travelu.feature.login.navigation.REGISTERED_EMAIL_KEY
import br.com.travelu.feature.login.navigation.loginScreen
import br.com.travelu.feature.signup.navigation.navigateToSignUp
import br.com.travelu.feature.signup.navigation.signUpScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
//        val viewModel = koinViewModel<TravelViewModel>()
//        ListingScreen(viewModel)
        TraveluNavHost()
    }
}

@Composable
fun TraveluNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = LoginRoute) {
        loginScreen(
            onSignUpClick = { navController.navigateToSignUp() },
            onLoginSuccess = { /* navigate to home later */ },
        )
        signUpScreen(
            onBackClick = { navController.popBackStack() },
            onSignInClick = { navController.popBackStack() },
            onRegisterSuccess = { email ->
                navController.previousBackStackEntry?.savedStateHandle?.set(REGISTERED_EMAIL_KEY, email)
                navController.popBackStack()
            },
        )
    }
}
