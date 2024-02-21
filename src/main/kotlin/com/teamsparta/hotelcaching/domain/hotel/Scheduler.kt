package com.teamsparta.hotelcaching.domain.hotel

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import com.teamsparta.hotelcaching.domain.hotel.service.HotelServiceImpl
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class Scheduler(
    private val historyRepository: HistoryRepository,
    private val hotelServiceImpl: HotelServiceImpl,
    private val redisTemplate: RedisTemplate<String, String>
) {


    @Transactional
    @Scheduled(initialDelay = 30000 , fixedRate = 60000)
    fun saveCachedSearchHistory() {
        val searchHistory = redisTemplate.opsForHash<String, String>().entries("searchHistory")

        searchHistory.forEach { (keyWord, searchNumberString) ->
            val searchNumber = searchNumberString.toLong()
            val existingHistory = historyRepository.findByKeyWord(keyWord)
            if (existingHistory != null) {
                existingHistory.searchNumber += searchNumber
//                historyRepository.save(existingHistory) 트랜잭션으로 관리
//                println("키워드 : ${keyWord}, 검색 횟수 ${existingHistory.searchNumber}")
            } else {
                val newHistory = HistoryEntity(keyWord = keyWord, searchNumber = searchNumber)
                historyRepository.save(newHistory)
//                println("새 키워드 : ${keyWord}, 검색 횟수: ${searchNumber}.")
            }

            hotelServiceImpl.deleteAllSearchHistoryInRedis()
        }
    }
}

