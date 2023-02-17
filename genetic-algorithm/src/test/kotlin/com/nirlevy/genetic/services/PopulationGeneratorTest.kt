package com.nirlevy.genetic.services

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.data.Population
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PopulationGeneratorTest {

    @MockK
    lateinit var chromosomeGenerator: ChromosomeGenerator<Int>

    lateinit var populationGenerator : PopulationGenerator<Int>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        populationGenerator = PopulationGenerator(chromosomeGenerator)
    }

    @Test
    internal fun replaceWeakest() {
        val c1 = Chromosome(listOf(1), 1.0)
        val c2 = Chromosome(listOf(2), 2.0)
        val c3 = Chromosome(listOf(3), 3.0)
        val c4 = Chromosome(listOf(4), 4.0)
        val c5 = Chromosome(listOf(5), 5.0)
        val c6 = Chromosome(listOf(6), 6.0)
        val population = Population(listOf(1, 2, 3, 4), 2, 4, listOf(c1, c2, c3, c4))
        val offspring = listOf(c5, c6)

        val newPopulation = populationGenerator.replaceWeakest(population, offspring)

        assertEquals(4, newPopulation.size)
        assertTrue(newPopulation.chromosomes.contains(c3))
        assertTrue(newPopulation.chromosomes.contains(c4))
        assertTrue(newPopulation.chromosomes.contains(c5))
        assertTrue(newPopulation.chromosomes.contains(c6))
    }
}