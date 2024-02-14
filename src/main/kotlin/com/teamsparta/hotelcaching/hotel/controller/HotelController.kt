package com.teamsparta.hotelcaching.hotel.controller

import com.teamsparta.hotelcaching.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.hotel.service.HotelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "hotel", description = "hotel API")
@RequestMapping("/api/hotels")
@RestController
class HotelController(
    private val hotelService: HotelService
) {

    @Operation(summary = "호텔 전체 조회")
    @GetMapping
    fun getHotelList(
        @RequestParam(name = "page", defaultValue = "0")
        page: Int,
        @RequestParam(name = "size", defaultValue = "10")
        size: Int
    ): ResponseEntity<List<HotelResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(hotelService.getHotelList(page, size))
    }
}