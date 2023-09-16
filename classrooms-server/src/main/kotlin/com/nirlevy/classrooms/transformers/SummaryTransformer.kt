package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.ClassSummary
import com.nirlevy.classrooms.model.ClassSummaryDto
import org.springframework.stereotype.Component

@Component
class SummaryTransformer {

    fun toDtoList(summaries: List<ClassSummary>): List<ClassSummaryDto> {
        return summaries.map { ClassSummaryDto(
            it.classNumber,
            it.studentsCount,
            it.malesCount,
            it.averageAcademicPerformance,
            it.averageBehaviouralPerformance,
            it.withoutFriends,
            it.unwantedMatches
        ) }
    }
}