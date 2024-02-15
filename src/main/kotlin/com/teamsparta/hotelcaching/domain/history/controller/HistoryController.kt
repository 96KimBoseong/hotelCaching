package com.teamsparta.hotelcaching.domain.history.controller

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import com.teamsparta.hotelcaching.domain.history.service.HistoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/searchHistory")
@RestController
class HistoryController(
    private val historyService: HistoryService
) {

    @GetMapping("/v1")
    fun searchTrend(
    ):ResponseEntity<List<HistoryResponse>>
    {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(historyService.trendKeyword())
    }
}