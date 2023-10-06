package com.nirlevy.classrooms.data

data class StudentCsvEntity(
    val name: String,
    val school: String,
    val gender: Gender,
    val academicPerformance: Grade,
    val behavioralPerformance: Grade,
    val comments: String,
    val friend1: String,
    val friend2: String,
    val friend3: String,
    val friend4: String,
    val notWith: String,
    var id: Int,
    val clusterId: Int
)
