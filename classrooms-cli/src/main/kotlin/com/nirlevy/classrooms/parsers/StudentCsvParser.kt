package com.nirlevy.classrooms.parsers

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nirlevy.classrooms.StudentCsvEntity
import com.nirlevy.classrooms.data.Student
import java.io.FileReader

class StudentCsvParser {

    private val csvMapper = CsvMapper().apply {
        registerModule(KotlinModule.Builder().build())
    }

    fun parseStudents(filename: String) : List<Student> {
        FileReader(filename).use { reader ->
            val studentCsvEntities = csvMapper
                .readerFor(StudentCsvEntity::class.java)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues<StudentCsvEntity>(reader)
                .readAll()
                .toList()
            return toStudentsList(studentCsvEntities)
        }
    }

    private fun toStudentsList(studentCsvEntities: List<StudentCsvEntity>): List<Student> {
        var i = 1
        studentCsvEntities.forEach { it.id = i++ }
        val studentIdentifiersToIds = studentCsvEntities.associateBy({StudentIdentifier(it.name, it.school)}, StudentCsvEntity::id)

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
                cantBeWith = toId(it, studentIdentifiersToIds)
            )
        }
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


