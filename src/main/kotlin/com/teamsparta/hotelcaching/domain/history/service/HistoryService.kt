package com.teamsparta.hotelcaching.domain.history.service

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse

interface HistoryService {
    //fun searchTrend(keyword:String):List<HistoryResponse>
    fun trendKeyword():List<HistoryResponse>

    fun saveKeyword(name:String)
}