package com.nirlevy.classrooms

import com.nirlevy.classrooms.data.ClassSummary
import com.nirlevy.classrooms.data.Classrooms
import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.evaluators.*
import com.nirlevy.genetic.GeneticSolver
import com.nirlevy.genetic.GroupEvaluator

class ClassroomsEngine {
    private val solver: GeneticSolver<Student>

    constructor() :
        this(PreferredFriendsEvaluator(),
            GenderBalanceEvaluator(),
            SizeBalanceEvaluator(),
            AcademicPerformanceEvaluator(),
            BehavioralPerformanceEvaluator(),
            SeparateFromEvaluator())

    constructor(vararg groupEvaluators: GroupEvaluator<Student>) {
        solver = GeneticSolver(*groupEvaluators)
    }

    fun buildClasses(students: List<Student>, numGroups: Int): Classrooms {
        val classes = solver.solve(students, numGroups)
        val summaries = buildSummaries(classes)
        return Classrooms(classes, summaries)
    }

    private fun buildSummaries(classes: Map<Int, List<Student>>): List<ClassSummary> {
        return classes.map { entry ->
            val classNumber = entry.key
            val students = entry.value
            val maleCount = students.count { it.gender == Gender.MALE }
            val averageAcademicPerformance = students.sumOf { it.academicPerformance.value } / students.size.toDouble()
            val averageBehaviouralPerformance = students.sumOf { it.behavioralPerformance.value } / students.size.toDouble()
            val friendsCount = getFriendsCount(students)
            val withoutFriends = friendsCount.count { it == 0 }
            val studentIds = students.map { it.id }.toSet()
            val unwantedMatches = students.count { student -> student.cantBeWith.any { it in studentIds } }
            ClassSummary(
                classNumber,
                students.size,
                maleCount,
                averageAcademicPerformance,
                averageBehaviouralPerformance,
                withoutFriends,
                unwantedMatches
            )
        }
    }

    private fun getFriendsCount(classroom: List<Student>) : List<Int> {
        return classroom.map {
            it.preferredFriends.intersect(classroom.map(Student::id).toSet()).size
        }
    }

}