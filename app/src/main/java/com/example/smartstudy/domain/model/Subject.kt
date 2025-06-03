package com.example.smartstudy.domain.model

import androidx.compose.ui.graphics.Color
import com.example.smartstudy.presentation.theme.gradient1
import com.example.smartstudy.presentation.theme.gradient2
import com.example.smartstudy.presentation.theme.gradient3
import com.example.smartstudy.presentation.theme.gradient4
import com.example.smartstudy.presentation.theme.gradient5

data class Subject(
    val name: String,
    val getHours: Float,
    val colors: List<Color>
){
    companion object{
        val subjectCardColor=listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
