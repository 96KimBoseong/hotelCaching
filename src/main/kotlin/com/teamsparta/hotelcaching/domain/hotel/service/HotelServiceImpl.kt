package com.teamsparta.hotelcaching.domain.hotel.service

import com.teamsparta.hotelcaching.domain.history.model.HistoryEntity
import com.teamsparta.hotelcaching.domain.history.repository.CustomHistoryRepository
import com.teamsparta.hotelcaching.domain.history.repository.HistoryRepository
import com.teamsparta.hotelcaching.domain.history.service.HistoryService
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.hotel.model.toResponse
import com.teamsparta.hotelcaching.domain.hotel.repository.HotelRepository
import com.teamsparta.hotelcaching.exception.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelServiceImpl(
    private val hotelRepository: HotelRepository,
    private val historyService: HistoryService

): HotelService {

    @Transactional(readOnly = true)
    override fun getHotelList(page: Int, size: Int): List<HotelResponse> {
        return hotelRepository.findAll().map { it.toResponse() }
    }

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

    override fun deleteHotel(hotelId: Long) {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel",hotelId)
        hotelRepository.delete(hotel)
    }



    //호텔 검색
    @Transactional
    override fun searchHotel(name: String): List<HotelResponse> {
        historyService.saveKeyword(name)
        //호텔이름을 검색하는 서비스, 호텔이름이 검색히스토리에 있다면 넘버를 증가/저장, 호텔이름이 없다면 저장 조회수는 1
        return hotelRepository.searchByHotelName(name).map { it.toResponse() }
    }

    //호텔검색 페이징 적용
    @Transactional
    override fun searchHotelPageList(name: String, page: Int,size:Int): Page<HotelResponse> {
        historyService.saveKeyword(name)
        return hotelRepository.searchByHotelNamePageList(name,page,size).map { it.toResponse() }
    }
}