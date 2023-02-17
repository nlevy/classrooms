package com.nirlevy.genetic.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GroupsUtilsTest {

    private val groupsUtils = GroupsUtils()

    @Test
    internal fun createMap() {
        val map = groupsUtils.createMap(listOf(1, 2, 3, 2, 2, 1), listOf("a", "b", "c", "d", "e", "f"))

        assertEquals(3, map.size)
        assertEquals(listOf("a","f"), map[1])
        assertEquals(listOf("b","d","e"), map[2])
        assertEquals(listOf("c"), map[3])
    }
}