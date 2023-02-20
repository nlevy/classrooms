package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SeparateFromEvaluatorTest {
    private val evaluator = SeparateFromEvaluator()

    @Test
    fun noDisallowedConnections() {
        val students = listOf(
            Student(1, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList()),
            Student(2, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList())
        )

        val fitness = evaluator.evaluate(8, 4, students)
        assertEquals(0.0, fitness, 0.001)
    }

    @Test
    fun withDisallowedConnection() {
        val students = listOf(
            Student(1, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList(), listOf(2)),
            Student(2, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList())
        )

        val fitness = evaluator.evaluate(8, 4, students)
        assertEquals(-400.0, fitness, 0.001)
    }

    @Test
    fun withOtherDisallowedConnection() {
        val students = listOf(
            Student(1, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList(), listOf(5)),
            Student(2, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList())
        )

        val fitness = evaluator.evaluate(8, 4, students)
        assertEquals(0.0, fitness, 0.001)
    }

    @Test
    fun withManyDisallowedConnections() {
        val students = listOf(
            Student(1, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList(), listOf(2)),
            Student(2, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList()),
            Student(3, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList()),
            Student(4, Gender.MALE, Grade.HIGH, Grade.HIGH, emptyList(), listOf(7, 1))
        )

        val fitness = evaluator.evaluate(8, 4, students)
        assertEquals(-800.0, fitness, 0.001)
    }

}