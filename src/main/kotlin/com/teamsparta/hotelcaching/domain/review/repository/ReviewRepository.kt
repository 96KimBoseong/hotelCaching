package com.teamsparta.hotelcaching.domain.review.repository

import com.teamsparta.hotelcaching.domain.review.model.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<ReviewEntity,Long> {
}