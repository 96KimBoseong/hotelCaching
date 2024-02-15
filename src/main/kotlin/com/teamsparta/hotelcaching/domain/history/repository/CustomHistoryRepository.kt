package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity

interface CustomHistoryRepository {
//    fun findBySearchNumber(searchNumber : Long) : List<HistoryEntity>

    fun findAll(): List<HistoryEntity>
}