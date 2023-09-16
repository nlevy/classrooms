package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.ClassSummary
import com.nirlevy.classrooms.data.Classrooms
import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.model.ClassSummaryDto
import com.nirlevy.classrooms.model.StudentDto
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClassroomsTransformerTest {

    @MockK private lateinit var summaryTransformer: SummaryTransformer
    @MockK private lateinit var studentTransformer: StudentTransformer

    @MockK private lateinit var classrooms: Map<Int, List<Student>>
    @MockK private lateinit var summaries: List<ClassSummary>

    @MockK private lateinit var expectedClassrooms: Map<Int, List<StudentDto>>
    @MockK private lateinit var expectedSummaries: List<ClassSummaryDto>

    private lateinit var transformer: ClassroomsTransformer

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        transformer = ClassroomsTransformer(studentTransformer, summaryTransformer)
    }

    @Test
    fun transform() {
        every { studentTransformer.toDtoList(classrooms) } returns expectedClassrooms
        every { summaryTransformer.toDtoList(summaries) } returns expectedSummaries

        val classroomsDto = transformer.toClassroomsDto(Classrooms(classrooms, summaries))

        assertSame(expectedClassrooms, classroomsDto.classes)
        assertSame(expectedSummaries, classroomsDto.summaries)
    }
}