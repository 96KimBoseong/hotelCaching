package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomHotelRepository {
    fun searchByHotelName(name:String):List<HotelEntity>

    fun searchByHotelNamePageList(name: String,page:Int,size:Int):Page<HotelEntity>
}