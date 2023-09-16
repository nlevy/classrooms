package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.Classrooms
import com.nirlevy.classrooms.model.ClassroomsDto
import org.springframework.stereotype.Component

@Component
class ClassroomsTransformer(
    private val studentTransformer: StudentTransformer,
    private val summaryTransformer: SummaryTransformer
) {

    fun toClassroomsDto(classes: Classrooms): ClassroomsDto {
        val classesDto = studentTransformer.toDtoList(classes.classes)
        val summaries = summaryTransformer.toDtoList(classes.classesSummaries)
        return ClassroomsDto(classesDto, summaries)
    }
}