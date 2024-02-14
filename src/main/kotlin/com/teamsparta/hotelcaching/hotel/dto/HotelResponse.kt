package com.teamsparta.hotelcaching.hotel.dto

data class HotelResponse(
    val id: Long,
    val name: String,
    val grade: Double,
    val content: String,
    val price: Long
)
