package com.teamsparta.hotelcaching.domain.history.model

import com.teamsparta.hotelcaching.domain.history.dto.HistoryResponse
import jakarta.persistence.*

@Entity
@Table(name = "history")
class HistoryEntity(

    @Column(name = "keyWord")
    var keyWord: String,

    @Column(name = "searchNumber")
    var searchNumber: Long,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

fun HistoryEntity.toResponse():HistoryResponse{
    return HistoryResponse(
        keyWord = keyWord
    )
}