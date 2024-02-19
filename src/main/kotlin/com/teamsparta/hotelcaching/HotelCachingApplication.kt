package com.teamsparta.hotelcaching

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableCaching
@SpringBootApplication
class HotelCachingApplication

fun main(args: Array<String>) {
    runApplication<HotelCachingApplication>(*args)
}
