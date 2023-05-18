package com.example.noteswithrestapi.core.utils

import java.time.LocalDateTime


fun formatStringDate(stringDate: String): String {
    val localDate = LocalDateTime.parse(stringDate)
    return localDate.toString()
}