package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.ClassSummary
import com.nirlevy.classrooms.model.ClassSummaryDto
import org.junit.jupiter.api.Test

class SummaryTransformerTest {


    private val transformer = SummaryTransformer()

    @Test
    fun toDtoList() {
        val summaries = listOf(
            ClassSummary(1, 25, 12, 2.0, 2.1, 0, 1),
            ClassSummary(2, 24, 12, 2.1, 2.2, 0, 0),
            ClassSummary(3, 25, 13, 2.1, 1.9, 0, 0)
        )
        val summaryDtos = transformer.toDtoList(summaries)

        kotlin.test.assertEquals(3, summaryDtos.size)
        kotlin.test.assertTrue(
            summaryDtos.containsAll(
                listOf(
                    ClassSummaryDto(1, 25, 12, 2.0, 2.1, 0, 1),
                    ClassSummaryDto(2, 24, 12, 2.1, 2.2, 0, 0),
                    ClassSummaryDto(3, 25, 13, 2.1, 1.9, 0, 0)
                )
            )
        )
    }
}