package com.nirlevy.genetic

/**
 * Evaluator for a single group of genes.
 */
interface GroupEvaluator<T> {

    /**
     * Evaluate the fitness of a single group.
     * @param numGenes The total number of genes.
     * @param numGroups The total number of groups.
     * @param group The group to evaluate.
     * @return The fitness of the group.
     */
    fun evaluate(
        numGenes: Int,
        numGroups: Int,
        group: List<T>
    ): Double
}