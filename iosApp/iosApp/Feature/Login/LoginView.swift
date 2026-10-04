import SwiftUI
import SharedLogic

// Ponte Swift <-> ViewModel compartilhado (mesmo LoginViewModel usado no Android).
final class LoginViewModelWrapper: ObservableObject {
    @Published var state: LoginUiState

    private let holder: ViewModelHolder<LoginViewModel>
    private var viewModel: LoginViewModel { holder.viewModel }

    init() {
        let holder = KoinHelperKt.getLoginViewModelHolder()
        self.holder = holder
        // Com SKIE, StateFlow<T> preserva o generic e .value já chega tipado.
        self.state = holder.viewModel.uiState.value
    }

    @MainActor
    func observe() async {
        for await newState in viewModel.uiState {
            state = newState
        }
    }

    func login() {
        viewModel.onLoginClicked(email: "teste@travelu.com", password: "123456")
    }

    deinit {
        holder.clear()
    }
}

struct LoginView: View {
    @StateObject private var wrapper = LoginViewModelWrapper()

    var body: some View {
        VStack(spacing: 16) {
            if wrapper.state.isLoading {
                ProgressView()
            } else if let userName = wrapper.state.userName {
                Text("Bem-vindo, \(userName)!")
            } else {
                Text(wrapper.state.errorMessage ?? "Faça login")
                Button("Entrar") {
                    wrapper.login()
                }
            }
        }
        .padding()
        .task {
            await wrapper.observe()
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
