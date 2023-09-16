package com.nirlevy.classrooms.model

data class ClassSummaryDto(
    val classNumber: Int,
    val studentsCount: Int,
    val malesCount: Int,
    val averageAcademicPerformance: Double,
    val averageBehaviouralPerformance: Double,
    val withoutFriends: Int
)
