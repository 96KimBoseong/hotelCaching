package com.teamsparta.hotelcaching.domain.review.dto

data class ReviewRequest(
    val content : String,
    val grade : Double,
    val nickName : String
)
