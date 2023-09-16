package com.nirlevy.classrooms.data

data class ClassSummary(
    val classNumber: Int,
    val studentsCount: Int,
    val malesCount: Int,
    val averageAcademicPerformance: Double,
    val averageBehaviouralPerformance: Double,
    val withoutFriends: Int,
    val unwantedMatches: Int
)
