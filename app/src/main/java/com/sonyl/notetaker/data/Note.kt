package com.sonyl.notetaker.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString().substring(0, 8),
    val text: String,
    val created: String = LocalDateTime.now()
        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)
