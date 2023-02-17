package com.nirlevy.genetic.data


class Population<T>(val genes: List<T>, val numGroups: Int, val size: Int, val chromosomes: List<Chromosome>) {

    fun selectParents(): List<Chromosome> {
        return chromosomes
            .sortedDescending()
            .take(chromosomes.size / 2)
    }
}
