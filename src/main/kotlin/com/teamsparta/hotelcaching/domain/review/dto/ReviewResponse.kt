package com.teamsparta.hotelcaching.domain.review.dto

import java.time.LocalDateTime

data class ReviewResponse(
    val id : Long,
    val content : String,
    val grade : Double,
    val nickName : String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
)
