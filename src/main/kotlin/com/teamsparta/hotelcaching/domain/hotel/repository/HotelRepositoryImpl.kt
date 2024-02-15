package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.QHotelEntity
import com.teamsparta.hotelcaching.infra.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class HotelRepositoryImpl:CustomHotelRepository, QueryDslSupport() {
    private val hotel = QHotelEntity.hotelEntity
    override fun searchByHotelName(name: String): List<HotelEntity> {
        return queryFactory.selectFrom(hotel)
            .where(hotel.name.contains(name))
            .fetch()
    }

    override fun searchByHotelNamePageList(name: String,page:Int,size:Int): Page<HotelEntity> {
            val result = queryFactory.selectFrom(hotel)
                .where(hotel.name.contains(name))
                .offset(page.toLong())
                .limit(size.toLong())
                .orderBy(hotel.name.asc())//이름순 정렬 할꺼 요구사항에 따라 변화
                .fetch()
        return PageImpl(result)
    }
}