package com.teamsparta.hotelcaching.hotel.service

import com.teamsparta.hotelcaching.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.hotel.model.toResponse
import com.teamsparta.hotelcaching.hotel.repository.HotelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelServiceImpl(
    private val hotelRepository: HotelRepository
): HotelService {

    @Transactional(readOnly = true)
    override fun getHotelList(page: Int, size: Int): List<HotelResponse> {
        return hotelRepository.findAll().map { it.toResponse() }
    }
}