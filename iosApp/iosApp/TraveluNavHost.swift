import SwiftUI

// Equivalente ao TraveluNavHost do Android (App.kt).
enum Route: Hashable {
    case signUp
}

struct TraveluNavHost: View {
    @State private var path: [Route] = []
    // Resultado do cadastro devolvido para o login (REGISTERED_EMAIL_KEY no Android).
    @State private var registeredEmail: String?

    var body: some View {
        NavigationStack(path: $path) {
            LoginView(
                registeredEmail: registeredEmail,
                onRegisteredEmailConsumed: { registeredEmail = nil },
                onSignUpClick: { path.append(.signUp) },
                onLoginSuccess: { /* navigate to home later */ }
            )
            .toolbar(.hidden, for: .navigationBar)
            .navigationDestination(for: Route.self) { route in
                switch route {
                case .signUp:
                    SignUpView(
                        onBackClick: popBackStack,
                        onSignInClick: popBackStack,
                        onRegisterSuccess: { email in
                            registeredEmail = email
                            popBackStack()
                        }
                    )
                    .toolbar(.hidden, for: .navigationBar)
                }
            }
        }
    }

    private func popBackStack() {
        guard !path.isEmpty else { return }
        path.removeLast()
    }
}
