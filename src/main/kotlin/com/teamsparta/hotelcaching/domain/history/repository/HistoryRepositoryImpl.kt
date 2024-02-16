package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.model.QHistoryEntity
import com.teamsparta.hotelcaching.infra.QueryDslSupport
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class HistoryRepositoryImpl:CustomHistoryRepository,QueryDslSupport() {
    private val history = QHistoryEntity.historyEntity

    //@Cacheable("saveHistory")
    override fun searchTrend():List<HistoryEntity> {
        return queryFactory.selectFrom(history)
            .orderBy(history.searchNumber.desc(),history.id.desc())//조회수가 동일할경우 일단 id값 오름차순으로 가장 최근의 트렌드니께
            .limit(5)
            .fetch()
    }
}

//일단은 list로 반환