package com.nirlevy.classrooms.evaluators

import com.nirlevy.classrooms.data.Student
import com.nirlevy.genetic.GroupEvaluator

class PreferredFriendsEvaluator(private val invalidGroupPenalty: Int = 200
                        ): GroupEvaluator<Student> {

    override fun evaluate(numGenes: Int, numGroups: Int, group: List<Student>): Double {
       return countStudentsWithoutFriends(group) * -1.0 * invalidGroupPenalty
    }

    private fun countStudentsWithoutFriends(group: List<Student>): Int {
        return group.count { !doesStudentHaveFriend(it, group) }
    }

    private fun doesStudentHaveFriend(student: Student, group: List<Student>): Boolean {
        val ids = group.map(Student::id)
        return student.preferredFriends.any { ids.contains(it) }
    }
}