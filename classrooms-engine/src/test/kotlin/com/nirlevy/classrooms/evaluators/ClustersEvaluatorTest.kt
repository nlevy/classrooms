package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ClustersEvaluatorTest {

    private val evaluator = ClustersEvaluator(50.0)

    @Test
    fun withOneViolatingClusterBrokenToTwo() {
        var id = 1
        val group1 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group2 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group3 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList())
        )

        val groups = mapOf(1 to group1, 2 to group2, 3 to group3)
        val fitness = evaluator.evaluate(groups)

        assertEquals(-50.0, fitness, 0.001)
    }

    @Test
    fun withoutViolatingCluster() {
        var id = 1
        val group1 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group2 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList())
        )
        val group3 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 5, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 5, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 5, emptyList()),
            Student(id, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 5, emptyList())
        )

        val groups = mapOf(1 to group1, 2 to group2, 3 to group3)
        val fitness = evaluator.evaluate(groups)

        assertEquals(0.0, fitness, 0.001)
    }

    @Test
    fun withOneViolatingClusterBrokenToThree() {
        var id = 1
        val group1 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group2 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group3 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )

        val groups = mapOf(1 to group1, 2 to group2, 3 to group3)
        val fitness = evaluator.evaluate(groups)

        assertEquals(-200.0, fitness, 0.001)
    }

    @Test
    fun withOneViolatingClusterBrokenToTwoAndOtherBrokenToThree() {
        var id = 1
        val group1 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group2 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group3 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList()),
            Student(id, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList())
        )

        val groups = mapOf(1 to group1, 2 to group2, 3 to group3)
        val fitness = evaluator.evaluate(groups)

        assertEquals(-250.0, fitness, 0.001)
    }

    @Test
    fun withNullClusters() {
        var id = 1
        val group1 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 1, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList())
        )
        val group2 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 0, emptyList())
        )
        val group3 = listOf(Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 4, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 0, emptyList()),
            Student(id++, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 2, emptyList()),
            Student(id, Gender.FEMALE, Grade.HIGH, Grade.HIGH, 3, emptyList())
        )

        val groups = mapOf(1 to group1, 2 to group2, 3 to group3)
        val fitness = evaluator.evaluate(groups)

        assertEquals(-250.0, fitness, 0.001)
    }

}