package com.teamsparta.hotelcaching.domain.hotel.dto

import java.io.Serializable

data class HotelResponse(
    val id: Long,
    val name: String,
    val grade: Double,
    val content: String,
    val price: Long
) : Serializable
