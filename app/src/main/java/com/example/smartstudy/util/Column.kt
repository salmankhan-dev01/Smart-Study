package com.example.smartstudy.util

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.presentation.theme.Green
import com.example.smartstudy.presentation.theme.Orange
import com.example.smartstudy.presentation.theme.Red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title: String,val color:Color,val value: Int){
    LOW(title = "Low", color = Green, value = 0),
    MEDIUM(title = "Medium", color = Orange, value = 1),
    HIGH(title = "High", color = Red, value = 2);

    companion object {
        fun formInt(value: Int) = values().firstOrNull{it.value==value}?:MEDIUM
    }
}

fun Long?.changeMillisToDateString(): String {
    val date: LocalDate= this?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MM yyyy"))
}