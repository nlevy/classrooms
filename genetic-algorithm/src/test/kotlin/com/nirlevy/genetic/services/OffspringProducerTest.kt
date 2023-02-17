package com.nirlevy.genetic.services

import com.nirlevy.genetic.data.Chromosome
import com.nirlevy.genetic.data.Population
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class OffspringProducerTest {

    @MockK(relaxed = true) lateinit var chromosomeEvaluator: ChromosomeEvaluator<Int>
    @MockK(relaxed = true) lateinit var population: Population<Int>

    private val parent1 = Chromosome(listOf(1,2), 1.1)
    private val parent2 = Chromosome(listOf(3,4), 2.2)

    @InjectMockKs
    lateinit var offspringProducer: OffspringProducer<Int>

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    internal fun produceOffspring() {
        val genes = listOf(1, 2, 3, 4)
        val numGroups = 2
        every { population.selectParents() } returnsMany listOf(listOf(parent1), listOf(parent2))
        every { population.numGroups } returns numGroups
        every { population.genes } returns genes
        every { chromosomeEvaluator.evaluateFitness(any(), genes, numGroups)} returns 1.1

        val offspring = offspringProducer.produceOffspring(population)

        assertEquals(1, offspring.size)
        assertEquals(1.1, offspring[0].fitness)
        assertEquals(2, offspring[0].size)
        assertTrue(offspring[0].genes.containsAll(listOf(1,2)))
    }
}