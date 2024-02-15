package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.model.QHistoryEntity
import com.teamsparta.hotelcaching.infra.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class HistoryRepositoryImpl:CustomHistoryRepository,QueryDslSupport() {

    private val history = QHistoryEntity.historyEntity

    override fun findAll(): List<HistoryEntity> {
        return queryFactory.select(history)
            .from(history)
            .orderBy(history.searchNumber.desc(),history.keyWord.desc(),history.id.asc())
            .limit(10)
            .fetch()
    }

//    override fun findBySearchNumber(searchNumber: Long): List<HistoryEntity> {
//        return queryFactory.select(history)
//            .from(history)
//            .where(history.searchNumber.eq(searchNumber))
//            .limit(10)
//            .orderBy(history.searchNumber.asc())
//            .fetch()
//    }
//    잘못만듬... 이건 검색 횟수로 찾는건데 목표는 이게 아님


}