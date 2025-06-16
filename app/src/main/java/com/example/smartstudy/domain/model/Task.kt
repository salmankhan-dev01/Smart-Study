package com.example.smartstudy.domain.model

import androidx.compose.foundation.MutatePriority

data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isComplete: String
)
