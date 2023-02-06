package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class GenderBalanceEvaluatorTest {
    private val evaluator = GenderBalanceEvaluator()

    @Test
    internal fun balancedClass() {
        val totalStudents = 10
        val students = buildStudentsList(totalStudents, true)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(20.0, fitness)
    }

    @Test
    internal fun imbalancedClass() {
        val totalStudents = 10
        val students = buildStudentsList(totalStudents, false)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(8.0, fitness)
    }

    @Test
    internal fun withFactor() {
        val totalStudents = 10
        val students = buildStudentsList(totalStudents, true)

        val fitness = GenderBalanceEvaluator(5).evaluate(8, 2, students)
        assertEquals(5.0, fitness)
    }

    private fun buildStudentsList(totalStudents: Int, balanced: Boolean): List<Student> {
        val students = IntStream.range(0, totalStudents).boxed().map {
            Student(
                it,
                if (balanced) if (it % 2 == 0) Gender.MALE else Gender.FEMALE else if (it % 5 == 0) Gender.MALE else Gender.FEMALE,
                Grade.HIGH,
                Grade.HIGH,
                emptyList()
            )
        }.collect(Collectors.toList())
        return students
    }
}