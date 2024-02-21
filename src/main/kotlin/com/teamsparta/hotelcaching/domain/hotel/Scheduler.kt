package com.teamsparta.hotelcaching.domain.hotel

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import com.teamsparta.hotelcaching.domain.hotel.service.HotelServiceImpl
import com.teamsparta.hotelcaching.domain.hotel.service.HotelServiceImpl.Companion.SEARCH_HISTORY_CACHE_KEY
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class Scheduler(private val historyRepository: HistoryRepository,
    private val redisTemplate: RedisTemplate<String,Any>,
) {
    @Scheduled(initialDelay = 180000 , fixedRate = 180000)
    fun saveCachedSearchHistory() {
        val entries = redisTemplate.opsForHash<String, String>().entries(SEARCH_HISTORY_CACHE_KEY)

        if(entries.isEmpty()){
            return
        }

        for ((name, searchNumString) in entries) {
            val findKeyword = historyRepository.findByKeyWord(name)
            val searchNum = searchNumString.toLong()

            if (findKeyword != null){
                findKeyword.searchNumber += searchNum
                historyRepository.save(findKeyword)
            }else{
                val history = HistoryEntity(keyWord = name, searchNumber = searchNum)
                historyRepository.save(history)
            }

        }
        redisTemplate.delete(SEARCH_HISTORY_CACHE_KEY)
    }

}