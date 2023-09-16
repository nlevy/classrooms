package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.Classrooms
import com.nirlevy.classrooms.model.ClassroomsDto
import org.springframework.stereotype.Component

@Component
class ClassroomsTransformer(private val studentTransformer: StudentTransformer) {

    fun toClassroomsDto(classes: Classrooms): ClassroomsDto {
        return ClassroomsDto(studentTransformer.toDtoList(classes.classes))
        // todo add summaries
    }

}