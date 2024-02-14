package com.teamsparta.hotelcaching.domain.review.service

import com.teamsparta.hotelcaching.domain.exception.ModelNotFoundException
import com.teamsparta.hotelcaching.domain.hotel.repository.HotelRepository
import com.teamsparta.hotelcaching.domain.review.dto.ReviewRequest
import com.teamsparta.hotelcaching.domain.review.dto.ReviewResponse
import com.teamsparta.hotelcaching.domain.review.model.ReviewEntity
import com.teamsparta.hotelcaching.domain.review.model.toResponse
import com.teamsparta.hotelcaching.domain.review.repository.ReviewRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val hotelRepository: HotelRepository
) {

    fun createReview(reviewRequest: ReviewRequest,hotelId:Long): ReviewResponse {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: throw ModelNotFoundException("Hotel", hotelId)
        val review = ReviewEntity(
            content = reviewRequest.content,
            grade = reviewRequest.grade,
            nickName = reviewRequest.nickName,
            hotel = hotel
        )
        val saveReview = reviewRepository.save(review)
        return saveReview.toResponse()
    }

    @Transactional
    fun updateReview(reviewRequest: ReviewRequest,reviewId:Long): ReviewResponse {
        val existingReview = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)

        existingReview.content = reviewRequest.content
        existingReview.grade = reviewRequest.grade
        existingReview.nickName = reviewRequest.nickName

        return existingReview.toResponse()
    }

    fun deleteReview(reviewId:Long) {
       val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)

        reviewRepository.delete(review)
    }
}