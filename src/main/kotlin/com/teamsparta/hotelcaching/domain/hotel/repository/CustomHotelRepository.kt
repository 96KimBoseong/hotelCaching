package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomHotelRepository {

    fun searchHotelListByName(name : String): List<HotelEntity>

    fun searchHotelListByNameWithPaging(name: String,pageable: Pageable) : Page<HotelEntity>

    fun searchHotelListByNameVersion2(name : String): List<HotelResponse>

    fun searchHotelListByNameWithPagingVersion2(name: String,pageable: Pageable) : Page<HotelResponse>

//    fun searchHotelListByNameWithPagingVersion2test(name: String,pageNumber: Int, pageSize: Int) : PageHotelResponse

//    fun searchHotelListByNameWithPagingVersion2test2(name: String,pageable: Pageable) : Page<HotelResponse>
}