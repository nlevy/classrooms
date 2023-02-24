package com.nirlevy.genetic.services

import com.nirlevy.genetic.data.Chromosome
import kotlin.random.Random

internal class ChromosomeGenerator<T>(private val chromosomeEvaluator: ChromosomeEvaluator<T>) {

    fun generateRandomChromosome(genes: List<T>, numGroups: Int): Chromosome {
        val chromosome = mutableListOf<Int>()
        for (i in 1..genes.size) {
            chromosome.add(Random.nextInt(numGroups) + 1)
        }
        return generateChromosome(chromosome, genes, numGroups)
    }

    private fun generateChromosome(chromosome: List<Int>, genes: List<T>, numGroups: Int): Chromosome {
        val fitness = chromosomeEvaluator.evaluateFitness(chromosome, genes, numGroups)
        return Chromosome(chromosome, fitness)
    }
}