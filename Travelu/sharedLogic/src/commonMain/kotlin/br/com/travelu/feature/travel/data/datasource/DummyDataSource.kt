package br.com.travelu.feature.travel.data.datasource

import br.com.travelu.feature.travel.data.model.TravelListingDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyDataSource {

    private val _listing = MutableStateFlow(getTravelListings())
    val listings = _listing.asStateFlow()
    fun getTravelListings(): List<TravelListingDto> = listOf(
        TravelListingDto(
            title = "Chalé na Serra",
            description = "Chalé aconchegante com vista para as montanhas.",
            location = "Campos do Jordão, SP",
            imageUrl = "https://picsum.photos/seed/chale/600/400",
            pricePerNight = 350.0,
            rating = 4.9,
            amenities = listOf("Wi-Fi", "Lareira", "Estacionamento"),
            hostName = "Marina",
        ),
        TravelListingDto(
            title = "Apartamento à Beira-Mar",
            description = "Vista para o mar com varanda privativa.",
            location = "Florianópolis, SC",
            imageUrl = "https://picsum.photos/seed/beira-mar/600/400",
            pricePerNight = 280.0,
            rating = 4.6,
            amenities = listOf("Wi-Fi", "Piscina", "Ar-condicionado"),
            hostName = "Carlos",
        ),
        TravelListingDto(
            title = "Casa de Campo",
            description = "Ambiente tranquilo cercado pela natureza.",
            location = "Monte Verde, MG",
            imageUrl = "https://picsum.photos/seed/campo/600/400",
            pricePerNight = 190.0,
            rating = 4.2,
            amenities = listOf("Wi-Fi", "Churrasqueira"),
            hostName = "Ana",
        ),
    )
}
