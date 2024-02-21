package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity

interface CustomHistoryRepository {
    fun findTrend(): List<HistoryResponse>
}