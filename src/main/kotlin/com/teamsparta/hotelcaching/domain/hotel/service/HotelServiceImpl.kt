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
    private val redisTemplate: RedisTemplate<String, Any>
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
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel",hotelId)
        hotelRepository.delete(hotel)
    }

    @Transactional
    override fun searchHotelList(name: String): List<HotelResponse> {
        saveSearchHistoryByRedis(name)
        return hotelRepository.searchHotelListByName(name).map { it.toResponse() }
    }

    @Transactional
    override fun searchHotelListWithPaging(name: String, pageable: Pageable): Page<HotelResponse> {
        saveSearchHistoryByRedis(name)
        return hotelRepository.searchHotelListByNameWithPaging(name,pageable).map { it.toResponse() }
    }

    @Transactional
    override fun searchHotelListVersion2(name: String): List<HotelResponse> {
        saveSearchHistoryByRedis(name)
        return hotelRepository.searchHotelListByNameVersion2(name)
    }

    @Transactional
    override fun searchHotelListWithPagingVersion2(name: String, pageable: Pageable): Page<HotelResponse> {
        saveSearchHistoryByRedis(name)
        return hotelRepository.searchHotelListByNameWithPagingVersion2(name,pageable)
    }

    override fun getPopularKeyWordBySearchNumber(): List<HistoryResponse> {
        val popularHistories = historyRepository.findAll()
        return popularHistories.map { HistoryResponse(it.keyWord) }
    }

    fun saveSearchHistoryByRedis(name: String) {
        redisTemplate.opsForHash<String,Long>().increment("searchHistory",name,1)
    }

//    fun getSearchHistoryCacheFromRedis(): Map<String, Long> {
//        val hash = redisTemplate.opsForHash<String,String>()
//        val searchHistoryInMap = hash.entries("searchHistory")
//        return searchHistoryInMap.mapValues { it.value.toLong() }
//    } //Map으로 한번 더 저장하는 안좋은 로직, 레디스->Map->DB로 오기 때문에 효율이 떨어진다.

    fun deleteAllSearchHistoryInRedis() {
        redisTemplate.delete("searchHistory")
    }

}

