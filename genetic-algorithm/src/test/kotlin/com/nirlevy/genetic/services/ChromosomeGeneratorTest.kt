package com.nirlevy.genetic.services

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ChromosomeGeneratorTest {

    @MockK
    lateinit var chromosomeEvaluator: ChromosomeEvaluator<Int>

    @InjectMockKs
    lateinit var chromosomeGenerator: ChromosomeGenerator<Int>

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun generate() {
        val genes = listOf(1, 2, 3, 4)
        every { chromosomeEvaluator.evaluateFitness(any(), genes, 2) } returns 10.0

        val chromosome = chromosomeGenerator.generateRandomChromosome(genes, 2)

        assertEquals(4, chromosome.size)
        assertEquals(10.0, chromosome.fitness)
        verify { chromosomeEvaluator.evaluateFitness(any(), genes, 2) }
    }
}