package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class SizeBalanceEvaluatorTest {
    private val evaluator = SizeBalanceEvaluator()

    @Test
    fun balancedClass() {
        val totalStudents = 4
        val students = buildStudentsList(totalStudents)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(40.0, fitness)
    }

    @Test
    fun imbalancedClass() {
        val totalStudents = 3
        val students = buildStudentsList(totalStudents)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(30.0, fitness)
    }

    @Test
    fun withFactor() {
        val totalStudents = 4
        val students = buildStudentsList(totalStudents)

        val fitness = SizeBalanceEvaluator(5).evaluate(8, 2, students)
        assertEquals(20.0, fitness)
    }

    private fun buildStudentsList(totalStudents: Int): List<Student> {
        val students = IntStream.range(0, totalStudents).boxed().map {
            Student(
                it,
                Gender.MALE,
                Grade.HIGH,
                Grade.HIGH,
                emptyList()
            )
        }.collect(Collectors.toList())
        return students
    }
}