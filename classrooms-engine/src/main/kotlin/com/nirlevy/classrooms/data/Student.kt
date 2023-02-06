package com.nirlevy.classrooms.data

data class Student(
    val id: Int,
    val gender: Gender,
    val academicPerformance: Grade,
    val behavioralPerformance: Grade,
    val preferredFriends: List<Int> = emptyList(),
    val cantBeWith: List<Int> = emptyList(),
    val name: String = "",
    val school: String = "",
    val comments: String? = null
)
