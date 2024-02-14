package com.teamsparta.hotelcaching.domain.review.controller

import com.teamsparta.hotelcaching.domain.review.dto.ReviewRequest
import com.teamsparta.hotelcaching.domain.review.dto.ReviewResponse
import com.teamsparta.hotelcaching.domain.review.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/reviews")
@RestController
class ReviewController(private val reviewService: ReviewService) {

    @PostMapping("/{hotelId}")
    fun createReview (request: ReviewRequest,@PathVariable hotelId : Long):ResponseEntity<ReviewResponse> {
        val createReview = reviewService.createReview(request,hotelId)
        return ResponseEntity.status(HttpStatus.CREATED).body(createReview)
    }

    @PatchMapping("/{reviewId}")
    fun updateReview (request: ReviewRequest, @PathVariable reviewId : Long):ResponseEntity<ReviewResponse> {
        val updateReview = reviewService.updateReview(request,reviewId)
        return ResponseEntity.status(HttpStatus.OK).body(updateReview)
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview (@PathVariable reviewId: Long):ResponseEntity<Unit> {
        reviewService.deleteReview(reviewId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}