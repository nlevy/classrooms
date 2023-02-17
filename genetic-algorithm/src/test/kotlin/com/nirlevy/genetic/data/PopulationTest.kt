package com.nirlevy.genetic.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PopulationTest {

    @Test
    internal fun selectParents() {
        val c1 = Chromosome(listOf(1), 1.0)
        val c2 = Chromosome(listOf(2), 2.0)
        val c3 = Chromosome(listOf(3), 3.0)
        val c4 = Chromosome(listOf(4), 4.0)
        val c5 = Chromosome(listOf(5), 5.0)
        val c6 = Chromosome(listOf(6), 6.0)
        val c7 = Chromosome(listOf(7), 7.0)
        val c8 = Chromosome(listOf(8), 8.0)
        val c9 = Chromosome(listOf(9), 9.0)
        val c10 = Chromosome(listOf(10), 10.0)
        val population = Population(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3, 10, listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10))

        val parents = population.selectParents()

        assertEquals(5, parents.size)
        Assertions.assertTrue(population.chromosomes.contains(c6))
        Assertions.assertTrue(population.chromosomes.contains(c7))
        Assertions.assertTrue(population.chromosomes.contains(c8))
        Assertions.assertTrue(population.chromosomes.contains(c9))
        Assertions.assertTrue(population.chromosomes.contains(c10))
    }
}