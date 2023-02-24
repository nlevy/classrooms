package com.nirlevy.genetic.services

internal class GroupsUtils {

    fun <T> createMap(groups: List<Int>, genes: List<T>): Map<Int, List<T>> {
        val map = HashMap<Int, MutableList<T>>()
        for ((index, gene) in genes.withIndex()) {
            map.computeIfAbsent(groups[index]) { ArrayList() }.add(gene)
        }
        return map
    }

}