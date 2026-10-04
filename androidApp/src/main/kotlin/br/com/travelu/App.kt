package br.com.travelu

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.travelu.feature.login.ui.LoginScreen
import br.com.travelu.feature.travel.presentation.TravelViewModel
import br.com.travelu.feature.travel.ui.ListingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
//        val viewModel = koinViewModel<TravelViewModel>()
//        ListingScreen(viewModel)
        LoginScreen()
    }
}
