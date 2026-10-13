import SwiftUI
import SharedLogic

// Ponte Swift <-> ViewModel compartilhado (mesmo LoginViewModel usado no Android).
final class LoginViewModelWrapper: ObservableObject {
    @Published private(set) var state: LoginUiState

    private let holder: ViewModelHolder<LoginViewModel>
    private var viewModel: LoginViewModel { holder.viewModel }

    init() {
        let holder = KoinHelperKt.getLoginViewModelHolder()
        self.holder = holder
        // Com SKIE, StateFlow<T> preserva o generic e .value já chega tipado.
        self.state = holder.viewModel.uiState.value
    }

    var canSubmit: Bool {
        !state.isLoading && !state.email.isBlank && !state.password.isBlank
    }

    @MainActor
    func observe() async {
        for await newState in viewModel.uiState {
            state = newState
        }
    }

    func onEmailChanged(_ email: String) {
        viewModel.onEmailChanged(email: email)
        syncState()
    }

    func onPasswordChanged(_ password: String) {
        viewModel.onPasswordChanged(password: password)
        syncState()
    }

    func onLoginClicked() {
        viewModel.onLoginClicked()
    }

    func onLogoutClicked() {
        viewModel.onLogoutClicked()
    }

    // As mudanças de texto são síncronas no ViewModel; lê o valor na hora para
    // o TextField não "voltar" enquanto a coleta assíncrona do Flow não chega.
    private func syncState() {
        state = viewModel.uiState.value
    }

    deinit {
        holder.clear()
    }
}

struct LoginView: View {
    var registeredEmail: String? = nil
    var onRegisteredEmailConsumed: () -> Void = {}
    var onForgotPasswordClick: () -> Void = {}
    var onSignUpClick: () -> Void = {}
    var onLoginSuccess: () -> Void = {}

    @StateObject private var wrapper = LoginViewModelWrapper()
    @FocusState private var focusedField: Field?

    private enum Field {
        case email, password
    }

    var body: some View {
        Group {
            if wrapper.state.isRestoringSession {
                ProgressView()
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else if let userName = wrapper.state.user?.name {
                LoggedInContent(userName: userName, onLogoutClicked: wrapper.onLogoutClicked)
            } else {
                loginForm
            }
        }
        .task {
            await wrapper.observe()
        }
        .onChange(of: registeredEmail, initial: true) { _, email in
            guard let email else { return }
            wrapper.onEmailChanged(email)
            onRegisteredEmailConsumed()
        }
        .onChange(of: wrapper.state.user?.name) { _, name in
            if name != nil { onLoginSuccess() }
        }
    }

    private var loginForm: some View {
        ScrollView {
            VStack(spacing: 0) {
                Spacer().frame(height: 96)

                Text("Sign in now")
                    .font(.system(size: 32))
                    .multilineTextAlignment(.center)
                    .padding(16)

                Text("Please sign in to continue our app")
                    .font(.system(size: 16))
                    .foregroundStyle(Color.traveluSecondaryText)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 16)

                Spacer().frame(height: 26)

                TraveluTextField(
                    placeholder: "Email Address",
                    text: Binding(get: { wrapper.state.email }, set: wrapper.onEmailChanged),
                    keyboardType: .emailAddress,
                    textContentType: .username
                )
                .focused($focusedField, equals: .email)
                .submitLabel(.next)
                .onSubmit { focusedField = .password }

                Spacer().frame(height: 16)

                TraveluTextField(
                    placeholder: "Password",
                    text: Binding(get: { wrapper.state.password }, set: wrapper.onPasswordChanged),
                    isPassword: true,
                    textContentType: .password
                )
                .focused($focusedField, equals: .password)
                .submitLabel(.go)
                .onSubmit(login)

                if let errorMessage = wrapper.state.errorMessage {
                    Text(errorMessage)
                        .font(.system(size: 14))
                        .foregroundStyle(.red)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 4)
                }

                HStack {
                    Spacer()
                    Button("Forgot Password?", action: onForgotPasswordClick)
                }
                .padding(16)

                TraveluPrimaryButton(
                    title: "Sign In",
                    isLoading: wrapper.state.isLoading,
                    isEnabled: wrapper.canSubmit,
                    action: login
                )

                HStack(spacing: 4) {
                    Text("Don't have an account?")
                        .foregroundStyle(Color.traveluSecondaryText)
                    Button("Sign up", action: onSignUpClick)
                }
                .padding(16)
            }
        }
        .scrollDismissesKeyboard(.interactively)
    }

    private func login() {
        guard wrapper.canSubmit else { return }
        focusedField = nil
        wrapper.onLoginClicked()
    }
}

private struct LoggedInContent: View {
    let userName: String
    let onLogoutClicked: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Text("Welcome, \(userName)!")
                .font(.system(size: 24))
                .multilineTextAlignment(.center)

            Button(action: onLogoutClicked) {
                Text("Log out")
                    .padding(.vertical, 8)
                    .padding(.horizontal, 16)
            }
            .buttonStyle(.borderedProminent)
            .buttonBorderShape(.capsule)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
    }
}

extension String {
    var isBlank: Bool {
        trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
    }
}

#Preview {
    LoginView()
}
