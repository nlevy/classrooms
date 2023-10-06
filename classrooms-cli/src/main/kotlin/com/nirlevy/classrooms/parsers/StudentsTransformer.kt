package com.nirlevy.classrooms.parsers

import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.data.StudentCsvEntity
import com.nirlevy.classrooms.data.StudentOutputCsvEntity

class StudentsTransformer {

    fun toStudentsList(studentCsvEntities: List<StudentCsvEntity>): List<Student> {
        var i = 0
        studentCsvEntities.forEach { it.id = i++ }
        val studentIdentifiersToIds = studentCsvEntities.associateBy({
            StudentIdentifier(
                it.name,
                it.school
            )
        }, StudentCsvEntity::id)

        return studentCsvEntities.map {
            Student(
                id = it.id,
                gender = it.gender,
                academicPerformance = it.academicPerformance,
                behavioralPerformance = it.behavioralPerformance,
                name = it.name,
                school = it.school,
                comments = it.comments,
                preferredFriends = friendsToIds(it, studentIdentifiersToIds),
                cantBeWith = toId(it, studentIdentifiersToIds),
                clusterId = it.clusterId
            )
        }
    }

    fun toStudentsOutputEntities(classes: Map<Int, List<Student>>): List<StudentOutputCsvEntity> {
        val studentIdsToIdentifiers = classes.values.flatten().associateBy(Student::id) {
            StudentIdentifier(
                it.name,
                it.school
            )
        }
        return classes.map {
            buildClass(it.key, it.value, studentIdsToIdentifiers)
        }.flatten()
    }

    private fun buildClass(classNumber: Int, students: List<Student>, idsToIdentifiers: Map<Int, StudentIdentifier>): List<StudentOutputCsvEntity> {
        return students.map {
            val friends = getFriends(it, idsToIdentifiers)
            StudentOutputCsvEntity(classNumber,
            it.name,
            it.school,
            it.gender,
            it.academicPerformance,
            it.behavioralPerformance,
            it.comments ?: "",
                friends[0],
                friends[1],
                friends[2],
                friends[3],
                if (it.cantBeWith.isEmpty()) "" else toName(it.cantBeWith[0], idsToIdentifiers),
                it.clusterId
            )
        }
    }

    private fun getFriends(student: Student, idsToIdentifiers: Map<Int, StudentIdentifier>): List<String> {
        val names = student.preferredFriends.map {
            toName(it, idsToIdentifiers)
        }
        return names.padList(4)
    }

    private fun toName(id: Int, idsToIdentifiers: Map<Int, StudentIdentifier>) :String {
        return idsToIdentifiers[id]?.name ?: ""
    }

    private fun List<String>.padList(desiredSize: Int): List<String> {
        if (size >= desiredSize) {
            return this
        }
        val result = this.toMutableList()
        while (result.size < desiredSize) {
            result.add("")
        }
        return result.toList()
    }

    private fun toId(it: StudentCsvEntity,
                     studentIdentifiersToIds: Map<StudentIdentifier, Int>): List<Int> {
        return if (it.notWith.isEmpty()) {
            emptyList()
        } else {
            listOf(studentIdentifiersToIds.getOrDefault(StudentIdentifier(it.notWith, it.school),0))
        }
    }

    private fun friendsToIds(it: StudentCsvEntity, studentIdentifiersToIds: Map<StudentIdentifier, Int>): List<Int> {
        return listOfNotNull(
            studentIdentifiersToIds[StudentIdentifier(it.friend1, it.school)],
            studentIdentifiersToIds[StudentIdentifier(it.friend2, it.school)],
            studentIdentifiersToIds[StudentIdentifier(it.friend3, it.school)],
            studentIdentifiersToIds[StudentIdentifier(it.friend4, it.school)]
        )
    }


    private data class StudentIdentifier(
        val name: String,
        val school: String
    )
}