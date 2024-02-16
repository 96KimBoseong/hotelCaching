package com.teamsparta.hotelcaching.domain.history.repository

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository:JpaRepository<HistoryEntity,Long> {

    fun findByKeyWord(keyWord:String) : HistoryEntity?

//    fun findBySearchNumber(searchNumber : Long) : List<HistoryEntity>
    @Cacheable("getPopularKeyWordBySearchNumber")
    override fun findAll(): List<HistoryEntity>
}