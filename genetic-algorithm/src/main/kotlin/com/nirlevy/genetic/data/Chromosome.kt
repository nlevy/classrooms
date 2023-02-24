package com.nirlevy.genetic.data

internal data class Chromosome(val genes: List<Int>, val fitness: Double) : Comparable<Chromosome> {
    val size = genes.size

    override fun compareTo(other: Chromosome): Int {
        return fitness.compareTo(other.fitness)
    }
}
