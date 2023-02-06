package com.nirlevy.classrooms.parsers

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.FileReader
import java.util.*
import java.util.stream.Collectors

class StudentCsvParser {

    fun parseStudents(filename: String) : List<Student> {

        val reader = FileReader(filename)
        val parser = CSVParser(reader, CSVFormat.Builder.create().setSkipHeaderRecord(true).setHeader().build())

        var i = 1
        val studentEntities = parser.records.map { record ->
            StudentEntity(
                id = i++,
                gender = parseGender(record.get("gender")),
                academicPerformance = parseGrade(record.get("academicPerformance")),
                behavioralPerformance = parseGrade(record.get("behavioralPerformance")),
                studentIdentifier = StudentIdentifier(
                    name = record.get("name"),
                    school = record.get("school")
                ),
                comments = record.get("comments"),
                friends = listOf(
                    record.get("friend1"),
                    record.get("friend2"),
                    record.get("friend3"),
                    record.get("friend4"),
                ),
                notWith = record.get("notWith")
            )
        }

        val studentIdentifiersToIds = studentEntities.stream().collect(Collectors.toMap(StudentEntity::studentIdentifier, StudentEntity::id))

        val toList = studentEntities.map {
            Student(
                id = it.id,
                gender = it.gender,
                academicPerformance = it.academicPerformance,
                behavioralPerformance = it.behavioralPerformance,
                name = it.studentIdentifier.name,
                school = it.studentIdentifier.school,
                comments = it.comments,
                preferredFriends = friendsToIds(it, studentIdentifiersToIds),
                cantBeWith = toId(it, studentIdentifiersToIds)
            )
        }.toList()
        return toList
    }

    private fun toId(it: StudentEntity,
                     studentIdentifiersToIds: MutableMap<StudentIdentifier, Int>): List<Int> {
        return if (it.notWith.isNullOrEmpty()) {
            emptyList()
        } else {
            listOf(studentIdentifiersToIds.getOrDefault(StudentIdentifier(it.notWith, it.studentIdentifier.school),0))
        }
    }

    private fun friendsToIds(
        it: StudentEntity,
        studentIdentifiersToIds: MutableMap<StudentIdentifier, Int>
    ) = it.friends.filter { name -> studentIdentifiersToIds.contains(StudentIdentifier(name, it.studentIdentifier.school))  }
        .map { name -> studentIdentifiersToIds[StudentIdentifier(name, it.studentIdentifier.school)]!! }.toList()

    private fun parseGrade(value: String) = Grade.valueOf(value.uppercase(Locale.getDefault()))
    private fun parseGender(value: String) = Gender.valueOf(value.uppercase(Locale.getDefault()))

    private data class StudentEntity(
        val id: Int,
        val gender: Gender,
        val academicPerformance: Grade,
        val behavioralPerformance: Grade,
        val studentIdentifier: StudentIdentifier,
        val comments: String?,
        val friends: List<String>,
        val notWith: String?
    )

    private data class StudentIdentifier(
        val name: String,
        val school: String
    )
}


