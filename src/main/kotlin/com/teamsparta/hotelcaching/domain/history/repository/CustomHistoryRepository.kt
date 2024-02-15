package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity

interface CustomHistoryRepository {
    fun searchTrend():List<HistoryEntity>
    fun findByKeyWord(keyword:String):HistoryEntity?
}