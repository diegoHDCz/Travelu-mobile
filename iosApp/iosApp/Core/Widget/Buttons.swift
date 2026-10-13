import SwiftUI

// Equivalente ao TraveluCircleImageButton do Android.
struct TraveluCircleImageButton: View {
    let systemName: String
    let accessibilityLabel: LocalizedStringKey
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: systemName)
                .font(.system(size: 18, weight: .semibold))
                .foregroundStyle(.primary)
                .frame(width: 48, height: 48)
                .background(Color.primary.opacity(0.1), in: Circle())
        }
        .buttonStyle(.plain)
        .accessibilityLabel(accessibilityLabel)
        .padding(16)
    }
}

// Botão principal de largura total com estado de loading (Sign In / Sign Up).
struct TraveluPrimaryButton: View {
    let title: LocalizedStringKey
    var isLoading: Bool = false
    var isEnabled: Bool = true
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            ZStack {
                if isLoading {
                    ProgressView()
                        .tint(.white)
                } else {
                    Text(title)
                        .fontWeight(.semibold)
                }
            }
            .frame(maxWidth: .infinity, minHeight: 52)
        }
        .buttonStyle(.borderedProminent)
        .buttonBorderShape(.roundedRectangle(radius: 12))
        .disabled(!isEnabled)
        .padding(.horizontal, 16)
    }
}

#Preview {
    VStack {
        TraveluCircleImageButton(systemName: "arrow.left", accessibilityLabel: "Back", action: {})
        TraveluPrimaryButton(title: "Sign In", action: {})
        TraveluPrimaryButton(title: "Sign In", isLoading: true, action: {})
    }
}
