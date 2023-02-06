package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.GroupEvaluator
import kotlin.math.abs

class GenderBalanceEvaluator(private val genderBalanceFactor: Int = 20) : GroupEvaluator<Student> {

    override fun evaluate(numGenes: Int, numGroups: Int, group: List<Student>): Double {
        val numMales = group.count { it.gender == Gender.MALE }
        val numFemales = group.size - numMales
        val balance = 1.0 - abs(numMales - numFemales) / group.size.toDouble()
        return genderBalanceFactor * balance
    }
}