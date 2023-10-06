package com.nirlevy.classrooms.parsers

import com.nirlevy.classrooms.data.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class StudentsTransformerTest {
    private val transformer = StudentsTransformer()

    @Test
    fun toStudentsList() {
        val entities = listOf(
            StudentCsvEntity("Bob","school1", Gender.MALE, Grade.LOW, Grade.HIGH,"bla", "jim", "", "", "","",0, 1),
            StudentCsvEntity("jim","school1", Gender.FEMALE, Grade.LOW, Grade.MEDIUM,"bla", "Bob", "", "", "","",1, 1)
        )
        val students = transformer.toStudentsList(entities)

        assertEquals(2, students.size)
        assertTrue(students.containsAll(listOf(
            Student(0,Gender.MALE, Grade.LOW, Grade.HIGH, 1, listOf(1), emptyList(), "Bob", "school1", "bla"),
            Student(1,Gender.FEMALE, Grade.LOW, Grade.MEDIUM, 1, listOf(0), emptyList(), "jim", "school1", "bla")
        )))
    }

    @Test
    fun toStudentsOutputEntities() {
        val classes : Map<Int,List<Student>> = mapOf(
            (1 to listOf(
                Student(1,  Gender.MALE, Grade.LOW, Grade.MEDIUM,1, listOf(2), emptyList(),"joe", "school1", "bla"),
                Student(2,  Gender.MALE, Grade.LOW, Grade.MEDIUM, 1, listOf(1), emptyList(),"bill", "school1", "bla"))),
            (2 to listOf(
                Student(3,  Gender.MALE, Grade.LOW, Grade.MEDIUM, 1, listOf(4), listOf(1),"bob", "school2", "bla"),
                Student(4,  Gender.FEMALE, Grade.LOW, Grade.MEDIUM, 1, listOf(3), emptyList(),"jenny", "school2", "bla"))),
        )
        val entities = transformer.toStudentsOutputEntities(classes)

        assertEquals(4, entities.size)
        assertTrue(entities.containsAll(listOf(
            StudentOutputCsvEntity(1, "joe", "school1", Gender.MALE, Grade.LOW, Grade.MEDIUM, "bla", "bill", "","","", "", 1),
            StudentOutputCsvEntity(1, "bill", "school1", Gender.MALE, Grade.LOW, Grade.MEDIUM, "bla", "joe", "","","", "", 1),
            StudentOutputCsvEntity(2, "bob", "school2", Gender.MALE, Grade.LOW, Grade.MEDIUM, "bla", "jenny", "","","", "joe", 1),
            StudentOutputCsvEntity(2, "jenny", "school2", Gender.FEMALE, Grade.LOW, Grade.MEDIUM, "bla", "bob", "","","", "", 1),
        )))
    }
}