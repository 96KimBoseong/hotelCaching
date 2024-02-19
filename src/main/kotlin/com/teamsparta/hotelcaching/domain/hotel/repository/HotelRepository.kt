package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository



interface HotelRepository: JpaRepository<HotelEntity, Long>,CustomHotelRepository {

    override fun searchHotelListByName(name : String): List<HotelEntity>

    override fun searchHotelListByNameWithPaging(name: String, pageable: Pageable) : Page<HotelEntity>

    @Cacheable(value = ["cacheTest"], key = "#name")
    override fun searchHotelListByNameVersion2(name : String): List<HotelEntity>

    @Cacheable(value = ["cacheTest"], key = "#name")
    override fun searchHotelListByNameWithPagingVersion2(name: String, pageable: Pageable) : Page<HotelEntity>

}