package com.teamsparta.hotelcaching.hotel.repository

import com.teamsparta.hotelcaching.hotel.model.HotelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HotelRepository: JpaRepository<HotelEntity, Long> {
}