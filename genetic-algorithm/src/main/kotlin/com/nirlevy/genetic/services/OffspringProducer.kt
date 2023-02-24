package com.nirlevy.genetic.services

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.data.Population
import kotlin.random.Random

internal class OffspringProducer<T>(private val chromosomeEvaluator: ChromosomeEvaluator<T>) {

    fun produceOffspring(population: Population<T>): List<Chromosome> {
        val offspring = mutableListOf<Chromosome>()
        val parents = population.selectParents()
        while (offspring.size < parents.size) {
            val parent1 = parents.random()
            val parent2 = parents.random()
            val child = crossover(population, parent1, parent2)
            offspring.add(child)
        }

        return offspring
    }

    private fun crossover(population: Population<T>, parent1: Chromosome, parent2: Chromosome): Chromosome {
        val crossoverPoint = Random.nextInt(parent1.size - 1) + 1
        val chromosome = parent1.genes.take(crossoverPoint) + parent2.genes.drop(crossoverPoint)
        val fitness = chromosomeEvaluator.evaluateFitness(chromosome, population.genes, population.numGroups)
        return Chromosome(chromosome, fitness)
    }
}
