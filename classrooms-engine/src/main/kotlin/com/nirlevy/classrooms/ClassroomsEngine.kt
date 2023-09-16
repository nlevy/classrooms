package com.nirlevy.classrooms

import com.nirlevy.classrooms.data.Classrooms
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
        return Classrooms(classes)
    }
}