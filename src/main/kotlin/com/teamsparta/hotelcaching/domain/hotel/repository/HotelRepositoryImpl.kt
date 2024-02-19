package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.QHotelEntity
import com.teamsparta.hotelcaching.infra.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class HotelRepositoryImpl: CustomHotelRepository,QueryDslSupport() {

    private val hotel = QHotelEntity.hotelEntity

    override fun searchHotelListByName(name : String): List<HotelEntity>{
        return queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .fetch()
    }

    override fun searchHotelListByNameWithPaging(name: String, pageable: Pageable): Page<HotelEntity> {

        val totalCount = queryFactory.select(hotel.count()).from(hotel).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(query,pageable,totalCount)
    }

    override fun searchHotelListByNameVersion2(name : String): List<HotelEntity>{
        return queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .fetch()
    }

    override fun searchHotelListByNameWithPagingVersion2(name: String, pageable: Pageable): Page<HotelEntity> {

        val totalCount = queryFactory.select(hotel.count()).from(hotel).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(query,pageable,totalCount)
    }

}