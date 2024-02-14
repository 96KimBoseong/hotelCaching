package com.teamsparta.hotelcaching.domain.hotel.dto

data class HotelRequest(
    val name : String,
    val content : String,
    val price : Long,
)
