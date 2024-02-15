package com.teamsparta.hotelcaching.domain.hotel.service

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface HotelService {
    fun getHotelList(page: Int, size: Int): List<HotelResponse>

    fun createHotel(request : HotelRequest):HotelResponse

    fun deleteHotel(hotelId : Long)

    fun searchHotelList(name: String) : List<HotelResponse>

    fun searchHotelListWithPaging(name: String , pageable: Pageable) : Page<HotelResponse>

    fun getPopularKeyWordBySearchNumber():List<HistoryResponse>
}