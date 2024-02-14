package com.teamsparta.hotelcaching.domain.hotel.service

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse

interface HotelService {
    fun getHotelList(page: Int, size: Int): List<HotelResponse>
}