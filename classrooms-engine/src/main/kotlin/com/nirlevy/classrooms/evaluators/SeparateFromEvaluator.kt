package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.GroupEvaluator

class SeparateFromEvaluator(private val separateFromPenalty: Int = 400
): GroupEvaluator<Student> {

    override fun evaluate(numGenes: Int, numGroups: Int, group: List<Student>): Double {
        val studentIds = group.map { it.id }.toSet()
        val count = group.count { student -> student.cantBeWith.any { it in studentIds } }
        return count * -1.0 * separateFromPenalty
    }
}