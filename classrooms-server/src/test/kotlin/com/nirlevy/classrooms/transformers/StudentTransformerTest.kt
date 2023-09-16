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
            StudentDto("Bob", "school1", Gender.MALE, Grade.LOW, Grade.HIGH, "bla", "jim", "", "", "", ""),
            StudentDto("jim", "school1", Gender.FEMALE, Grade.LOW, Grade.MEDIUM, "bla", "Bob", "", "", "", "")
        )
        val students = transformer.toStudentsList(entities)

        kotlin.test.assertEquals(2, students.size)
        kotlin.test.assertTrue(
            students.containsAll(
                listOf(
                    Student(0, Gender.MALE, Grade.LOW, Grade.HIGH, listOf(1), emptyList(), "Bob", "school1", "bla"),
                    Student(1, Gender.FEMALE, Grade.LOW, Grade.MEDIUM, listOf(0), emptyList(), "jim", "school1", "bla")
                )
            )
        )
    }
}