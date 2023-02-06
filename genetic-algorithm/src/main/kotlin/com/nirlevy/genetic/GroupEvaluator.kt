package com.nirlevy.genetic

interface GroupEvaluator<T> {
    fun evaluate(
        numGenes: Int,
        numGroups: Int,
        group: List<T>
    ): Double
}