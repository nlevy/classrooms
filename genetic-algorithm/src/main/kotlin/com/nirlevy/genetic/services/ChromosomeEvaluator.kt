package com.nirlevy.genetic.services

import com.nirlevy.genetic.GroupEvaluator

class ChromosomeEvaluator<T>(private vararg val groupEvaluators: GroupEvaluator<T>) {

    fun evaluateFitness(chromosome: List<Int>, genes: List<T>, numGroups: Int): Double {
        val groupsMap = createMap(chromosome, genes)

        var fitness = 0.0
        for (group in groupsMap.values) {
            fitness += groupEvaluators.sumOf { it.evaluate(genes.size, numGroups, group) }
        }

        return fitness / (numGroups * 3)
    }

    private fun <T> createMap(groups: List<Int>, genes: List<T>): Map<Int, List<T>> {
        val map = HashMap<Int, MutableList<T>>()
        for ((index, gene) in genes.withIndex()) {
            map.computeIfAbsent(groups[index]) { ArrayList() }.add(gene)
        }
        return map
    }
}
