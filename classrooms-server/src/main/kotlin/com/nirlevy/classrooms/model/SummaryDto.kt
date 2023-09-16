package com.nirlevy.classrooms.model

data class SummaryDto(
    val classesCount: Int,
    val maleCount: Int,
    val averageAcademicPerformance: Double,
    val averageBehaviouralPerformance: Double
)
