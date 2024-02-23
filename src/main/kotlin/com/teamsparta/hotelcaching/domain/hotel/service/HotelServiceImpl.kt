package com.teamsparta.hotelcaching.domain.hotel.service

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.toResponse
import com.teamsparta.hotelcaching.domain.hotel.repository.HotelRepository
import com.teamsparta.hotelcaching.exception.ModelNotFoundException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelServiceImpl(
    private val hotelRepository: HotelRepository,
    private val historyRepository: HistoryRepository,
    private val redisTemplate: RedisTemplate<String,Any>
): HotelService {

    @Transactional(readOnly = true)
    override fun getHotelList(page: Int, size: Int): List<HotelResponse> {
        return hotelRepository.findAll().map { it.toResponse() }
    }

    @CacheEvict(value = ["cacheTest"], allEntries = true)
    override fun createHotel(request: HotelRequest): HotelResponse {
        val hotel = HotelEntity(
            content = request.content,
            grade = 0.0,
            name = request.name,
            price = request.price
        )
        val saveHotel = hotelRepository.save(hotel)
        return saveHotel.toResponse()
    }

    @CacheEvict(value = ["cacheTest"], allEntries = true)
    override fun deleteHotel(hotelId: Long) {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel", hotelId)
        hotelRepository.delete(hotel)
    }

    @Transactional
    override fun searchHotelList(name: String): List<HotelResponse> {
        saveSearchHistory(name)
        return hotelRepository.searchHotelListByName(name).map { it.toResponse() }
    }

    @Transactional
    override fun searchHotelListWithPaging(name: String, pageable: Pageable): Page<HotelResponse> {
        saveSearchHistory(name)
        return hotelRepository.searchHotelListByNameWithPaging(name, pageable).map { it.toResponse() }
    }

    @Transactional
    override fun searchHotelListVersion2(name: String): List<HotelResponse> {
        saveSearchHistoryToCache(name)
        return hotelRepository.searchHotelListByNameVersion2(name)
    }

    @Transactional
    override fun searchHotelListWithPagingVersion2(name: String, pageable: Pageable): Page<HotelResponse> {
        saveSearchHistoryToCache(name)
        return hotelRepository.searchHotelListByNameWithPagingVersion2(name, pageable)
    }

    override fun getPopularKeyWordBySearchNumber(): List<HistoryResponse> {
        val popularHistories = historyRepository.findTrend()
        return popularHistories
    }

    fun saveSearchHistory(keyword: String) {
        val existingHistory = historyRepository.findByKeyWord(keyword)

        if (existingHistory != null) {
            existingHistory.searchNumber++
            historyRepository.save(existingHistory)
        } else {
            val newHistory = HistoryEntity(keyWord = keyword, searchNumber = 1)
            historyRepository.save(newHistory)
        }
    }
    fun saveSearchHistoryToCache(name: String) {
        redisTemplate.opsForHash<String, Long>().increment(SEARCH_HISTORY_CACHE_KEY, name, 1)
    }

    companion object {
        const val SEARCH_HISTORY_CACHE_KEY = "search_history"
    }
}

