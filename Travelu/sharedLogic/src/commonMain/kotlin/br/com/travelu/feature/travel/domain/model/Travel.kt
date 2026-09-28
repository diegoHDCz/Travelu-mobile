package br.com.travelu.feature.travel.domain.model

data class Travel(
    val title: String,
    val description: String,
    val location: String,
    val imageUrl: String,
    val pricePerNight: Double,
    val rating: Double,
    val amenities: List<String> = emptyList(),
    val hostName: String,
    val isFavorite: Boolean = false
) {
    fun getFormattedPrice(): String {
        return "$${(pricePerNight)} / night"
    }

    fun getRatingTier(): RatingTier {
        return when {
            rating >= 4.8 -> RatingTier.EXCEPTIONAL
            rating >= 4.5 -> RatingTier.EXCELLENT
            rating >= 4.0 -> RatingTier.VERY_GOOD
            rating >= 3.5 -> RatingTier.GOOD
            rating >= 3.0 -> RatingTier.AVERAGE
            else -> RatingTier.BELOW_AVERAGE
        }
    }
}

enum class RatingTier {
    EXCEPTIONAL,
    EXCELLENT,
    VERY_GOOD,
    GOOD,
    AVERAGE,
    BELOW_AVERAGE
}

