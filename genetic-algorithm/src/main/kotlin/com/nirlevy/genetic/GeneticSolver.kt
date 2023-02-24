package com.nirlevy.genetic

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.services.*

class GeneticSolver<T>(
    vararg groupEvaluators: GroupEvaluator<T>,
    private val fitnessThreshold: Double = 150.0,
    private val numGenerations: Int = 5000,
    private val populationSize: Int = 10000) {

    private val groupsUtils: GroupsUtils = GroupsUtils()
    private val chromosomeEvaluator = ChromosomeEvaluator(groupsUtils, *groupEvaluators)
    private val offspringProducer = OffspringProducer(chromosomeEvaluator)
    private val populationGenerator = PopulationGenerator(ChromosomeGenerator(chromosomeEvaluator))

    fun solve(genes: List<T>, numGroups: Int): Map<Int, List<T>> {
        return groupsUtils.createMap(runSolver(genes, numGroups).genes, genes)
    }

    private fun runSolver(genes: List<T>, numGroups: Int) : Chromosome {
        var population = populationGenerator.generatePopulation(populationSize, genes, numGroups)
        var previousBest = 0.0
        var consecutiveEqualBest = 0
        var shuffleCount = 0

        for (generation in 1..numGenerations) {
            val offspring = offspringProducer.produceOffspring(population)
            population = populationGenerator.replaceWeakest(population, offspring)

            val maxFit = population.chromosomes.max().fitness
            if (maxFit >= fitnessThreshold) {
                println("Found solution on $generation generation")
                break
            } else {
                println("Generation $generation max score is $maxFit ($consecutiveEqualBest)")
                if (maxFit == previousBest) {
                    if (++consecutiveEqualBest == 10) {
                        if (++shuffleCount == 5) {
                            println("already shuffled 5 times, stopping...")
                            break
                        }
                        println("10 times with same max score $maxFit , shuffling (${shuffleCount} shuffle)")
                        population = populationGenerator.shuffle(population, numGroups)
                        consecutiveEqualBest = 0
                    }
                } else {
                    previousBest = maxFit
                    consecutiveEqualBest = 0
                }
            }
        }
        val best = population.chromosomes.max()
        println("winning group fitness: ${best.fitness}")

        return best
    }
}
