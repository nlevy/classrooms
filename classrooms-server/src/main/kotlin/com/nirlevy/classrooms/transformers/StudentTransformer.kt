package com.nirlevy.classrooms.transformers

import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.model.StudentDto
import org.springframework.stereotype.Component

@Component
class StudentTransformer {

    fun toStudentsList(studentCsvEntities: List<StudentDto>): List<Student> {
        var i = 0
        val studentDtoWithIds = studentCsvEntities.map { StudentDtoWithId(it, i++) }
        val studentIdentifiersToIds = studentDtoWithIds.associateBy({
            StudentIdentifier(
                it.student.name,
                it.student.school
            )
        }, StudentDtoWithId::id)

        return studentDtoWithIds.map {
            Student(
                id = it.id,
                gender = it.student.gender,
                academicPerformance = it.student.academicPerformance,
                behavioralPerformance = it.student.behavioralPerformance,
                name = it.student.name,
                school = it.student.school,
                comments = it.student.comments,
                preferredFriends = friendsToIds(it.student, studentIdentifiersToIds),
                cantBeWith = toId(it.student, studentIdentifiersToIds),
                clusterId = it.student.clusterId ?: 0
            )
        }
    }

    fun toDtoList(classes: Map<Int, List<Student>>): Map<Int, List<StudentDto>> {

        val studentIdsToIdentifiers = classes.values.flatten().associateBy(Student::id) {
            StudentIdentifier(
                it.name,
                it.school
            )
        }
        return classes.mapValues {
            buildClass(it.value, studentIdsToIdentifiers)
        }
    }

    private fun buildClass(students: List<Student>, idsToIdentifiers: Map<Int, StudentIdentifier>): List<StudentDto> {
        return students.map {
            val friends = getFriends(it, idsToIdentifiers)
            StudentDto(
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

    private fun toId(it: StudentDto,
                     studentIdentifiersToIds: Map<StudentIdentifier, Int>): List<Int> {
        return if (it.notWith?.isEmpty() != false) {
            emptyList()
        } else {
            listOf(studentIdentifiersToIds.getOrDefault(StudentIdentifier(it.notWith, it.school),0))
        }
    }

    private fun friendsToIds(it: StudentDto, studentIdentifiersToIds: Map<StudentIdentifier, Int>): List<Int> {
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

    private data class StudentDtoWithId(
        val student: StudentDto,
        val id: Int
    )
}