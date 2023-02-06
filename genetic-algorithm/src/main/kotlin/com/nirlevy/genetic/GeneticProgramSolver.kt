package com.nirlevy.genetic

import kotlin.math.min
import kotlin.random.Random

class GeneticProgramSolver<T>(private val populationSize: Int = 1000,
                              private val numGenerations: Int = 5000,
                              private val fitnessThreshold: Double = 150.0,
                              private val groupEvaluators: List<GroupEvaluator<T>>) {

    fun solve(genes: List<T>, numGroups: Int): Map<Int, List<T>> {
        return createMap(runSolver(genes, numGroups), genes)
    }

    private fun runSolver(genes: List<T>, numGroups: Int): List<Int> {
        var population = (1..populationSize).map {
            generateRandomChromosome(genes.size, numGroups)
        }
        var fitnesses = population.map { chromosome ->
            evaluateFitness(chromosome, genes, numGroups)
        }

        var previousBest = 0.0
        var consecutiveEqualBest = 0
        var shuffleCount = 0
        for (generation in 1..numGenerations) {
            val parents = selectParents(population, fitnesses)
            val offspring = produceOffspring(parents, numGroups)
            val offspringFitnesses = offspring.map { chromosome ->
                evaluateFitness(chromosome, genes, numGroups)
            }
            population = replaceWeakest(population, fitnesses, offspring, offspringFitnesses)
            fitnesses = replaceWeakest(fitnesses, offspringFitnesses)

            val maxFit = fitnesses.max()
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
                        population = shuffle(population, fitnesses, numGroups)
                        fitnesses = calculateFitnesses(population, genes, numGroups)
                        consecutiveEqualBest = 0
                    }
                } else {
                    previousBest = maxFit
                    consecutiveEqualBest = 0
                }
            }
        }

        val bestIndex = fitnesses.indexOf(fitnesses.max())
        println("winning group fitness: ${fitnesses[bestIndex]}")

        return population[bestIndex]
    }

    private fun calculateFitnesses(population: List<List<Int>>, genes: List<T>, numGroups: Int): List<Double> {
        return population.map { chromosome ->
            evaluateFitness(chromosome, genes, numGroups)
        }
    }

    private fun shuffle(population: List<List<Int>>, fitnesses: List<Double>, numGroups: Int): List<List<Int>> {
        val distinctFitnesses = fitnesses.distinct().sortedDescending()
        val newPopulation = mutableListOf<List<Int>>()
        val groupsToKeep = min(distinctFitnesses.size, population.size / 50)
        for (i in 0 until groupsToKeep) {
            val index = fitnesses.indexOf(distinctFitnesses[i])
            newPopulation.add(population[index])
        }
        while (newPopulation.size< population.size) {
            newPopulation.add(generateRandomChromosome(population[0].size, numGroups))
        }

        return newPopulation
    }

    private fun generateRandomChromosome(numGenes: Int, newGroups: Int): List<Int> {
        val chromosome = mutableListOf<Int>()
        for (i in 1..numGenes) {
            chromosome.add(Random.nextInt(newGroups) + 1)
        }
        return chromosome
    }

    private fun evaluateFitness(chromosome: List<Int>, genes: List<T>, numGroups: Int): Double {
        val groupsMap = createMap(chromosome, genes)

        var fitness = 0.0
        for (group in groupsMap.values) {
            fitness += groupEvaluators.sumOf { it.evaluate(genes.size, numGroups, group) }
        }

        return fitness / (numGroups * 3)
    }

    private fun selectParents(population: List<List<Int>>, fitnesses: List<Double>): List<List<Int>> {
        val parents = mutableListOf<List<Int>>()

        val populationSize = population.size
        val numParents = populationSize / 2
        val sortedFitnesses = fitnesses.sortedDescending()
        for (i in 0 until numParents) {
            val index = fitnesses.indexOf(sortedFitnesses[i])
            parents.add(population[index])
        }

        return parents
    }

    private fun produceOffspring(parents: List<List<Int>>, numGroups: Int): List<List<Int>> {
        val offspring = mutableListOf<List<Int>>()

        while (offspring.size < parents.size) {
            val parent1 = parents.random()
            val parent2 = parents.random()
            val child = crossover(parent1, parent2)
            offspring.add(child)
        }

        // Use mutation to introduce random variations in the offspring
        for (child in offspring) {
            mutate(child, numGroups)
        }

        return offspring
    }

    private fun crossover(parent1: List<Int>, parent2: List<Int>): List<Int> {
        val crossoverPoint = Random.nextInt(parent1.size - 1) + 1
        return parent1.take(crossoverPoint) + parent2.drop(crossoverPoint)
    }

    private fun mutate(chromosome: List<Int>, numGroups: Int): List<Int> {
        val mutationRate = 0.5
        val mutableChromosome = chromosome.toMutableList()
        for (i in chromosome.indices) {
            if (Random.nextDouble() < mutationRate) {
                mutableChromosome[i] = Random.nextInt(numGroups) + 1
            }
        }
        return mutableChromosome
    }

    private fun replaceWeakest(population: List<List<Int>>, fitnesses: List<Double>, offspring: List<List<Int>>, offspringFitnesses: List<Double>): List<List<Int>> {
        val combinedPopulation = (population + offspring).zip(fitnesses + offspringFitnesses)
        combinedPopulation.sortedBy { it.second }
        return combinedPopulation.takeLast(population.size).map { it.first }
    }

    private fun replaceWeakest(fitnesses: List<Double>, offspringFitnesses: List<Double>): List<Double> {
        val combinedFitnesses = fitnesses + offspringFitnesses
        combinedFitnesses.sorted()
        return combinedFitnesses.takeLast(fitnesses.size)
    }

    private fun <T> createMap(groups: List<Int>, genes: List<T>): Map<Int, List<T>> {
        val map = HashMap<Int, MutableList<T>>()
        for ((index, gene) in genes.withIndex()) {
            map.computeIfAbsent(groups[index]) { ArrayList() }.add(gene)
        }
        return map
    }
}


