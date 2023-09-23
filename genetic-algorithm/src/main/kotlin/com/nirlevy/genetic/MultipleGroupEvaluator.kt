package com.nirlevy.genetic

/**
 * Evaluator for multiple groups of genes. Allows evaluating cross-group functionality.
 */
interface MultipleGroupEvaluator<T> {

    /**
     * Evaluate the fitness of multiple groups.
     * @param groups The groups to evaluate.
     * @return The fitness of the groups.
     */
    fun evaluate(
        groups: Map<Int, List<T>>
    ): Double
}