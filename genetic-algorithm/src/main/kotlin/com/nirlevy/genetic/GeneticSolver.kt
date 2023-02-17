package com.nirlevy.genetic

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.services.OffspringProducer
import com.nirlevy.genetic.services.PopulationGenerator

class GeneticSolver<T>(
    private val offspringProducer: OffspringProducer<T>,
    private val populationGenerator: PopulationGenerator<T>,
    private val fitnessThreshold: Double = 150.0,
    private val numGenerations: Int = 5000) {

    fun solve(populationSize: Int, genes: List<T>, numGroups: Int) : Chromosome {
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
