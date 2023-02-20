package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.stream.Collectors
import java.util.stream.IntStream

internal class BehavioralPerformanceEvaluatorTest {
    private val evaluator = BehavioralPerformanceEvaluator()

    @Test
    fun balancedClass() {
        val totalStudents = 3
        val students = buildStudentsList(totalStudents, false)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(10.0, fitness)
    }

    @Test
    fun imbalancedClass() {
        val totalStudents = 3
        val students = buildStudentsList(totalStudents, true)

        val fitness = evaluator.evaluate(8, 2, students)
        assertEquals(10.0 / 3, fitness, 0.0001)
    }

    @Test
    fun withFactor() {
        val totalStudents = 3
        val students = buildStudentsList(totalStudents, true)

        val fitness = BehavioralPerformanceEvaluator(5).evaluate(8, 2, students)
        assertEquals(5.0 / 3, fitness, 0.0001)
    }

    private fun buildStudentsList(totalStudents: Int, strongClass: Boolean): List<Student> {
        val students = IntStream.range(0, totalStudents).boxed().map {
            Student(
                it,
                Gender.MALE,
                Grade.HIGH,
                if (strongClass) if (it == 0) Grade.MEDIUM else Grade.HIGH else Grade.fromValue(it+1),
                emptyList()
            )
        }.collect(Collectors.toList())
        return students
    }
}