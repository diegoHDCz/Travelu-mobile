import SwiftUI

extension Color {
    // Mesmo laranja do Android (core/theme/Color.kt).
    static let traveluOrange = Color(red: 1.0, green: 0x70 / 255.0, blue: 0x29 / 255.0)

    // Equivalentes aos tons do MaterialTheme usados nas telas do Android.
    static let traveluSecondaryText = Color.primary.opacity(0.7)
    static let traveluFieldBackground = Color(.secondarySystemBackground)
}
