package com.teamsparta.hotelcaching.domain.hotel.repository

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.QHotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.toResponse
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

    override fun searchHotelListByNameVersion2(name : String): List<HotelResponse>{
        val query = queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .fetch()

        val hotelResponse = query.map{ it.toResponse() }

        return hotelResponse
    }

    override fun searchHotelListByNameWithPagingVersion2(name: String, pageable: Pageable): Page<HotelResponse> {
        val totalCount = queryFactory.select(hotel.count()).from(hotel).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(hotel)
            .where(hotel.name.containsIgnoreCase(name))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val hotelResponse =query.map { it.toResponse()}

        return PageImpl(hotelResponse,pageable,totalCount)
    }

}

//    override fun searchHotelListByNameWithPagingVersion2test(
//        name: String,
//        pageNumber: Int,
//        pageSize: Int
//    ): PageHotelResponse {
//        val totalCount = queryFactory.select(hotel.count()).from(hotel)
//            .where(hotel.name.containsIgnoreCase(name))
//            .fetchOne() ?: 0L
//
//        val query = queryFactory.selectFrom(hotel)
//            .where(hotel.name.containsIgnoreCase(name))
//            .offset(((pageNumber) * pageSize).toLong())
//            .limit(pageSize.toLong())
//            .fetch()
//
//        val hotelResponseList = query.map { it.toResponse() }
//
//        return PageHotelResponse(
//            totalCount = totalCount,
//            hotels = hotelResponseList
//        )
//    }