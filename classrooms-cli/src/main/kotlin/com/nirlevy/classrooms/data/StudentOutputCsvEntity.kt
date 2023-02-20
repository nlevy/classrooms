package com.nirlevy.classrooms.data

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("classroom","name","school","gender","academicPerformance","behavioralPerformance","friend1","friend2","friend3","friend4","notWith","comments")
data class StudentOutputCsvEntity(
    val classroom: Int,
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
    val notWith: String
)
