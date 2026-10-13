import Foundation
import SharedLogic

// Equivalente ao RatingTierUi.kt.
extension RatingTier {
    var displayName: String {
        switch self {
        case .exceptional: String(localized: "Exceptional")
        case .excellent: String(localized: "Excellent")
        case .veryGood: String(localized: "Very Good")
        case .good: String(localized: "Good")
        case .average: String(localized: "Average")
        case .belowAverage: String(localized: "Below Average")
        }
    }
}
