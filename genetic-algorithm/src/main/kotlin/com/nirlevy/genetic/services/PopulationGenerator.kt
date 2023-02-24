package com.nirlevy.genetic.services

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.data.Population
import kotlin.math.min

internal class PopulationGenerator<T>(private val chromosomeGenerator: ChromosomeGenerator<T>) {

    fun generatePopulation(populationSize: Int, genes: List<T>, numGroups: Int) : Population<T> {
        val chromosomes = (1..populationSize).map {
            chromosomeGenerator.generateRandomChromosome(genes, numGroups)
        }
        return Population(genes, numGroups, populationSize, chromosomes)
    }

    fun replaceWeakest(population: Population<T>, offspring: List<Chromosome>): Population<T> {
        val combinedPopulation = (population.chromosomes + offspring)
        return Population(population.genes, population.numGroups, population.size, combinedPopulation.takeLast(population.size))
    }

    fun shuffle(population: Population<T>, numGroups: Int): Population<T> {
        val  chromosomes = population.chromosomes.distinct().sortedDescending()

        val groupsToKeep = min(chromosomes.size, population.size / 50)
        val newPopulation = chromosomes.take(groupsToKeep).toMutableList()

        while (newPopulation.size< population.size) {
            newPopulation.add(chromosomeGenerator.generateRandomChromosome(population.genes, numGroups))
        }

        return Population(population.genes, numGroups, population.size, newPopulation)
    }
}