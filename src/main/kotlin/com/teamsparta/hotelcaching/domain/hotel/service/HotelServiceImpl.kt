package com.teamsparta.hotelcaching.domain.hotel.service

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.toResponse
import com.teamsparta.hotelcaching.domain.hotel.repository.HotelRepository
import com.teamsparta.hotelcaching.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
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

    override fun createHotel(request: HotelRequest): HotelResponse {
        val hotel = HotelEntity(
            content = request.content,
            grade = 0.0,
            name = request.name,
            price = request.price
        )
        val saveHotel = hotelRepository.save(hotel)
        return saveHotel.toResponse()
    }

    override fun deleteHotel(hotelId: Long) {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel",hotelId)
        hotelRepository.delete(hotel)
    }
}