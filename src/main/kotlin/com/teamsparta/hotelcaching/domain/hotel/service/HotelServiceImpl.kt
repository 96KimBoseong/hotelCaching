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
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelServiceImpl(
    private val hotelRepository: HotelRepository,
    private val historyRepository: HistoryRepository
): HotelService {

    @Transactional(readOnly = true)
    override fun getHotelList(page: Int, size: Int): List<HotelResponse> {
        return hotelRepository.findAll().map { it.toResponse() }
    }

//    @CacheEvict(value = ["cacheTest"],allEntries = true)
//    @CachePut(value = ["cacheTest"])
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

//    @CacheEvict(value = ["cacheTest"],allEntries = true)
//    @CachePut(value = ["cacheTest"])
    @CacheEvict(value = ["cacheTest"], key = "#hotelId",)
    override fun deleteHotel(hotelId: Long) {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel",hotelId)
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
        return hotelRepository.searchHotelListByNameWithPaging(name,pageable).map { it.toResponse() }
    }

    override fun getPopularKeyWordBySearchNumber(): List<HistoryResponse> {
        val popularHistories = historyRepository.findAll()
        return popularHistories.map { HistoryResponse(it.keyWord) }
    }


    fun saveSearchHistory(keyWord : String) {
        val existingHistory = historyRepository.findByKeyWord(keyWord)

        if(existingHistory != null) {
            existingHistory.searchNumber++
            historyRepository.save(existingHistory)
        } else {
            val newHistory = HistoryEntity(keyWord = keyWord, searchNumber = 1)
            historyRepository.save(newHistory)
        }
    }

}

