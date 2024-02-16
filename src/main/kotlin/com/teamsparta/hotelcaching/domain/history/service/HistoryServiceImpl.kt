package com.teamsparta.hotelcaching.domain.history.service

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.model.toResponse
import com.teamsparta.hotelcaching.domain.history.repository.CustomHistoryRepository
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HistoryServiceImpl(
    private val historyRepository: HistoryRepository
):HistoryService {


    @Cacheable("trend")
    override fun trendKeyword(): List<HistoryResponse> {
        return historyRepository.searchTrend().map { it.toResponse() }
    }

    @Transactional
    @Cacheable("saveKeyword")
    override fun saveKeyword(name: String) {
        var findKeyword = historyRepository.findByKeyWord(name)
        if (findKeyword != null){
            findKeyword.searchNumber++
            historyRepository.save(findKeyword)
        }else{
            findKeyword = HistoryEntity(
                keyWord = name,
                searchNumber = 1
            )
            historyRepository.save(findKeyword)
        }
    }
}