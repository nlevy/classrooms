package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.GroupEvaluator
import kotlin.math.abs

class BehavioralPerformanceEvaluator(private val behavioralPerformanceFactor: Int = 10): GroupEvaluator<Student> {

    override fun evaluate(numGenes: Int, numGroups: Int, group: List<Student>): Double {
        val performance = 1 - abs(Grade.MEDIUM.value - group.sumOf { it.behavioralPerformance.value } / group.size.toDouble())
        return behavioralPerformanceFactor * performance
    }
}