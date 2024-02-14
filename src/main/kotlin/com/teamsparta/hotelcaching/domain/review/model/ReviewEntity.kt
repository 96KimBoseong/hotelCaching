package com.teamsparta.hotelcaching.domain.review.model

import com.teamsparta.hotelcaching.domain.hotel.model.HotelEntity
import com.teamsparta.hotelcaching.domain.review.dto.ReviewResponse
import com.teamsparta.hotelcaching.infra.BaseTimeEntity
import jakarta.persistence.*


@Entity
@Table(name = "review")
class ReviewEntity(

    @Column(name = "name")
    var content: String,

    @Column(name = "grade")
    var grade: Double,

    @Column(name = "nickName")
    var nickName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    val hotel: HotelEntity


):BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun ReviewEntity.toResponse(): ReviewResponse {
    return ReviewResponse(
        id = id!!,
        content = content,
        grade = grade,
        nickName = nickName,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}