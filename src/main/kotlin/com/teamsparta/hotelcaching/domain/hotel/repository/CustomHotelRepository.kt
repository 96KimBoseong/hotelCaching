package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomHotelRepository {

    fun searchHotelListByName(name : String): List<HotelEntity>

    fun searchHotelListByNameWithPaging(name: String,pageable: Pageable) : Page<HotelEntity>
}