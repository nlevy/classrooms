package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.model.StudentDto
import org.junit.jupiter.api.Test

class StudentTransformerTest {
    private val transformer = StudentTransformer()

    @Test
    fun toStudentsList() {
        val entities = listOf(
            StudentDto("Bob", "school1", Gender.MALE, Grade.LOW, Grade.HIGH, "bla", "jim", "", "", "", "", 1),
            StudentDto("jim", "school1", Gender.FEMALE, Grade.LOW, Grade.MEDIUM, "bla", "Bob", "", "", "", "", 2)
        )
        val students = transformer.toStudentsList(entities)

        kotlin.test.assertEquals(2, students.size)
        kotlin.test.assertTrue(
            students.containsAll(
                listOf(
                    Student(0, Gender.MALE, Grade.LOW, Grade.HIGH, 1, listOf(1), emptyList(), "Bob", "school1", "bla"),
                    Student(1, Gender.FEMALE, Grade.LOW, Grade.MEDIUM, 2, listOf(0), emptyList(), "jim", "school1", "bla")
                )
            )
        )
    }
}