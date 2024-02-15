package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HotelRepository: JpaRepository<HotelEntity, Long>,CustomHotelRepository {


}