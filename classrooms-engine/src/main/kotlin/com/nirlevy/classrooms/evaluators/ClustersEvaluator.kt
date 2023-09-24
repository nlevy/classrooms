package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.MultipleGroupEvaluator

class ClustersEvaluator(private val brokenClusterPenalty: Double = 20.0): MultipleGroupEvaluator<Student> {

    override fun evaluate(groups: Map<Int, List<Student>>): Double {
        val clusterCountMap = countGroupsByCluster(groups)
        val brokenClusters = clusterCountMap.map { it.value - 1 }.sumOf { it * it }

        return brokenClusterPenalty * brokenClusters * -1.0
    }

    private fun countGroupsByCluster(groups: Map<Int, List<Student>>): Map<Int, Int> {
        val clusterCountMap = mutableMapOf<Int, Int>()

        for (group in groups.values) {
            val clustersInGroup = group.map { it.clusterId }.filter { it > 0 }.distinct()
            for (cluster in clustersInGroup) {
                clusterCountMap[cluster] = clusterCountMap.getOrDefault(cluster, 0) + 1
            }
        }

        return clusterCountMap
    }

}