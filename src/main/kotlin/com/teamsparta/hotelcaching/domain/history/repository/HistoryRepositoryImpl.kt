package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.model.QHistoryEntity
import com.teamsparta.hotelcaching.domain.history.model.toResponse
import com.teamsparta.hotelcaching.infra.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class HistoryRepositoryImpl:CustomHistoryRepository,QueryDslSupport() {

    private val history = QHistoryEntity.historyEntity

    override fun findTrend(): List<HistoryResponse> {
        return queryFactory.select(history)
            .from(history)
            .orderBy(history.searchNumber.desc(),history.keyWord.desc(),history.id.asc())
            .limit(10)
            .fetch()
            .map { it.toResponse() }


    }


}