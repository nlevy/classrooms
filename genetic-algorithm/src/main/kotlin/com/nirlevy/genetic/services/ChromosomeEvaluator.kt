package com.nirlevy.genetic.services

import com.nirlevy.genetic.GroupEvaluator
import com.nirlevy.genetic.MultipleGroupEvaluator

internal class ChromosomeEvaluator<T>(private val groupsUtils: GroupsUtils,
                                      private val groupEvaluators: List<GroupEvaluator<T>>,
                                      private val multipleGroupEvaluator: List<MultipleGroupEvaluator<T>>) {

    fun evaluateFitness(chromosome: List<Int>, genes: List<T>, numGroups: Int): Double {
        val groupsMap = groupsUtils.createMap(chromosome, genes)

        var fitness = 0.0
        for (group in groupsMap.values) {
            fitness += groupEvaluators.sumOf { it.evaluate(genes.size, numGroups, group) }
        }

        fitness += multipleGroupEvaluator.sumOf { it.evaluate(groupsMap) }
        return fitness / (numGroups * 3)
    }

}
