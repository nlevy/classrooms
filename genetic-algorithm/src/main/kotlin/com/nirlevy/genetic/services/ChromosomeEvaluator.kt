package com.nirlevy.genetic.services

import com.nirlevy.genetic.GroupEvaluator

internal class ChromosomeEvaluator<T>(private val groupsUtils: GroupsUtils, private vararg val groupEvaluators: GroupEvaluator<T>) {

    fun evaluateFitness(chromosome: List<Int>, genes: List<T>, numGroups: Int): Double {
        val groupsMap = groupsUtils.createMap(chromosome, genes)

        var fitness = 0.0
        for (group in groupsMap.values) {
            fitness += groupEvaluators.sumOf { it.evaluate(genes.size, numGroups, group) }
        }

        return fitness / (numGroups * 3)
    }

}
