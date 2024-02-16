package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository



interface HotelRepository: JpaRepository<HotelEntity, Long>,CustomHotelRepository {

    @Cacheable(value = ["cacheTest"], key = "#name")
    override fun searchHotelListByName(name : String): List<HotelEntity>

    @Cacheable(value = ["cacheTest"], key = "#name")
    override fun searchHotelListByNameWithPaging(name: String, pageable: Pageable) : Page<HotelEntity>



}