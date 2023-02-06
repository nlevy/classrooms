package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.GroupEvaluator
import kotlin.math.abs

class SizeBalanceEvaluator(private val sizeBalanceFactor: Int = 10): GroupEvaluator<Student> {
    override fun evaluate(numGenes: Int, numGroups: Int, group: List<Student>): Double {
        val expectedSize = numGenes / numGroups
        return (sizeBalanceFactor * (expectedSize - abs(expectedSize - group.size))).toDouble()

    }
}