package com.teamsparta.hotelcaching.hotel.service

import com.teamsparta.hotelcaching.hotel.dto.HotelResponse

interface HotelService {
    fun getHotelList(page: Int, size: Int): List<HotelResponse>
}