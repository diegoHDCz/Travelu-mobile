package br.com.travelu.feature.travel.data.mapper

import br.com.travelu.feature.travel.data.model.TravelListingDto
import br.com.travelu.feature.travel.domain.model.Travel

object TravelMapper {
    fun toDomain(dto: TravelListingDto): Travel = Travel(
        title = dto.title,
        description = dto.description,
        location = dto.location,
        imageUrl = dto.imageUrl,
        pricePerNight = dto.pricePerNight,
        rating = dto.rating,
        amenities = dto.amenities,
        hostName = dto.hostName,
        isFavorite = dto.isFavorite,
    )

    fun toDomain(dtos: List<TravelListingDto>): List<Travel> {
        return dtos.map { toDomain(it) }
    }

    fun toDto(domain: Travel): TravelListingDto = TravelListingDto(
        title = domain.title,
        description = domain.description,
        location = domain.location,
        imageUrl = domain.imageUrl,
        pricePerNight = domain.pricePerNight,
        rating = domain.rating,
        amenities = domain.amenities,
        hostName = domain.hostName,
        isFavorite = domain.isFavorite,
    )
}