package com.teamsparta.hotelcaching.domain.hotel.controller

import com.teamsparta.hotelcaching.domain.hotel.dto.HotelRequest
import com.teamsparta.hotelcaching.domain.hotel.dto.HotelResponse
import com.teamsparta.hotelcaching.domain.hotel.service.HotelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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

    @PostMapping
    fun createHotel(request: HotelRequest):ResponseEntity<HotelResponse> {
        val createHotel = hotelService.createHotel(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createHotel)
    }

    @DeleteMapping("/{hotelId}")
    fun deleteHotel(@PathVariable hotelId: Long):ResponseEntity<Unit> {
        hotelService.deleteHotel(hotelId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
    //호텔검색
//    @GetMapping("/v1/search")
//    fun searchHotel(
//        @RequestParam(name = "name") name:String,
//        @PageableDefault(
//            size = 10,
//            sort = ["name"]
//        ) pageable: Pageable
//    ):ResponseEntity<Page<HotelResponse>>
//    {
//        return ResponseEntity
//            .status(HttpStatus.OK)
//            .body(hotelService.searchHotelPageList(name,pageable))
//    }
//}

    @GetMapping("/v1/search")
    fun searchHotel(
        @RequestParam(name = "name") name:String,
        @RequestParam(value = "page") page: Int,
        @RequestParam(value = "size")size: Int

    ):ResponseEntity<Page<HotelResponse>>
    {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(hotelService.searchHotelPageList(name,page,size))
    }
}