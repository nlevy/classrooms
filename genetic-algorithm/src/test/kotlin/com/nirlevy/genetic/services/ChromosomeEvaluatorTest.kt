package com.nirlevy.genetic.services

import com.nirlevy.genetic.GroupEvaluator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ChromosomeEvaluatorTest {

    @MockK lateinit var evaluator1: GroupEvaluator<Int>
    @MockK lateinit var evaluator2: GroupEvaluator<Int>

    lateinit var chromosomeEvaluator: ChromosomeEvaluator<Int>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        chromosomeEvaluator = ChromosomeEvaluator(evaluator1, evaluator2)
    }

    @Test
    internal fun evaluate() {
        every { evaluator1.evaluate(4,2, listOf(1,2)) } returns 1.0
        every { evaluator2.evaluate(4,2, listOf(1,2)) } returns 2.0
        every { evaluator1.evaluate(4,2, listOf(3,4)) } returns 3.0
        every { evaluator2.evaluate(4,2, listOf(3,4)) } returns 4.0

        val fitness = chromosomeEvaluator.evaluateFitness(listOf(1, 1, 2, 2), listOf(1, 2, 3, 4), 2)

        assertEquals(10.0/6.0, fitness)
    }
}