package com.nirlevy.classrooms.parsers

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.data.StudentCsvEntity
import com.nirlevy.classrooms.data.StudentOutputCsvEntity
import java.io.File
import java.io.FileReader

class StudentCsvParser(private val studentsTransformer: StudentsTransformer) {

    private val csvMapper = CsvMapper().apply {
        registerModule(KotlinModule.Builder().build())
    }
    private val inputSchema = CsvSchema.emptySchema().withHeader()
    private val outputSchema = csvMapper.schemaFor(StudentOutputCsvEntity::class.java).withHeader()

    fun parseStudents(filename: String) : List<Student> {
        FileReader(filename).use { reader ->
            val studentCsvEntities = csvMapper
                .readerFor(StudentCsvEntity::class.java)
                .with(inputSchema)
                .readValues<StudentCsvEntity>(reader)
                .readAll()
                .toList()
            return studentsTransformer.toStudentsList(studentCsvEntities)
        }
    }

    fun writeToCsv(filename: String, classes: Map<Int, List<Student>>) {
        val students = studentsTransformer.toStudentsOutputEntities(classes).sortedBy { it.classroom }
        File(filename).bufferedWriter().use { writer ->
            csvMapper.writer(outputSchema)
                .writeValues(writer)
                .writeAll(students)
        }
    }
}


