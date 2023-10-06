package com.nirlevy.classrooms.model

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade

data class StudentDto(
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
    val notWith: String?,
    val clusterId: Int?
)
