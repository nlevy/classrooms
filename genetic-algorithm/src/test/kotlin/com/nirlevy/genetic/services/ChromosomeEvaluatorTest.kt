package com.nirlevy.genetic.services

import com.nirlevy.genetic.GroupEvaluator
import com.nirlevy.genetic.MultipleGroupEvaluator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ChromosomeEvaluatorTest {

    @MockK lateinit var evaluator1: GroupEvaluator<Double>
    @MockK lateinit var evaluator2: GroupEvaluator<Double>
    @MockK lateinit var multipleGroupEvaluator: MultipleGroupEvaluator<Double>

    private lateinit var chromosomeEvaluator: ChromosomeEvaluator<Double>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        chromosomeEvaluator = ChromosomeEvaluator(GroupsUtils(), listOf(evaluator1, evaluator2), listOf(multipleGroupEvaluator))
    }

    @Test
    fun evaluate() {
        every { evaluator1.evaluate(4,2, listOf(1.0,2.0)) } returns 1.0
        every { evaluator2.evaluate(4,2, listOf(1.0,2.0)) } returns 2.0
        every { evaluator1.evaluate(4,2, listOf(3.0,4.0)) } returns 3.0
        every { evaluator2.evaluate(4,2, listOf(3.0,4.0)) } returns 4.0
        every { multipleGroupEvaluator.evaluate(mapOf(1 to listOf(1.0, 2.0), 2 to listOf(3.0, 4.0))) } returns 5.0

        val fitness = chromosomeEvaluator.evaluateFitness(listOf(1, 1, 2, 2), listOf(1.0, 2.0, 3.0, 4.0), 2)

        assertEquals(15.0/6.0, fitness)
    }
}