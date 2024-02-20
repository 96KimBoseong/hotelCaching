package com.teamsparta.hotelcaching.domain.hotel

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import com.teamsparta.hotelcaching.domain.hotel.service.HotelServiceImpl
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class Scheduler(private val historyRepository: HistoryRepository,
    hotelServiceImpl: HotelServiceImpl
) {

    val searchHistoryToCache = hotelServiceImpl.searchHistoryToCacheInService

    @Scheduled(initialDelay = 3000000 , fixedRate = 3000000)
    fun saveCachedSearchHistory() {
        for ((keyWord, searchNumber) in searchHistoryToCache) {
            val existingHistory = historyRepository.findByKeyWord(keyWord)
            if (existingHistory != null) {
                existingHistory.searchNumber += searchNumber
                historyRepository.save(existingHistory)
//                println("키워드 : ${keyWord}, 검색 횟수 ${existingHistory.searchNumber}")
            } else {
                val newHistory = HistoryEntity(keyWord = keyWord, searchNumber = searchNumber)
                historyRepository.save(newHistory)
//                println("새 키워드 : ${keyWord}, 검색 횟수: ${searchNumber}.")
            }
        }
        searchHistoryToCache.clear()
    }

}