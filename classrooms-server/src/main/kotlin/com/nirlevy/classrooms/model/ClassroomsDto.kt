package com.nirlevy.classrooms.model

data class ClassroomsDto(
    val classes: Map<Int, List<StudentDto>>
    // todo add summaries

)