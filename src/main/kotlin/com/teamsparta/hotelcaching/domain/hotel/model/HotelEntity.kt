package com.teamsparta.hotelcaching.domain.hotel.model

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import jakarta.persistence.*

@Entity
@Table(name = "hotel")
class HotelEntity(

    @Column(name = "name")
    val name: String,

    @Column(name = "grade")
    val grade: Double,

    @Column(name = "content")
    val content: String,

    @Column(name = "price")
    val price: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun HotelEntity.toResponse(): HotelResponse {
    return HotelResponse(
        id = id!!,
        name = name,
        grade = grade,
        content = content,
        price = price
    )
}