package br.com.travelu.feature.travel.data.model

data class TravelListingDto(
    val title: String,
    val description: String,
    val location: String,
    val imageUrl: String,
    val pricePerNight: Double,
    val rating: Double,
    val amenities: List<String> = emptyList(),
    val hostName: String,
    val isFavorite: Boolean = false
)
