package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository:JpaRepository<HistoryEntity,Long>,CustomHistoryRepository {
    fun findByKeyWord(keyword:String):HistoryEntity?


}