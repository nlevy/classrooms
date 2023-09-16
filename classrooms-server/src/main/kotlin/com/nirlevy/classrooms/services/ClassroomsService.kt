package com.nirlevy.classrooms.services

import com.nirlevy.classrooms.ClassroomsEngine
import com.nirlevy.classrooms.model.ClassroomsDto
import com.nirlevy.classrooms.model.StudentDto
import com.nirlevy.classrooms.transformers.ClassroomsTransformer
import com.nirlevy.classrooms.transformers.StudentTransformer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class ClassroomsService(
    private val classroomsTransformer: ClassroomsTransformer,
    private val studentTransformer: StudentTransformer,
    private val classroomsEngine: ClassroomsEngine
) {
    private val logger: Logger = LogManager.getLogger(ClassroomsService::class.java)

    fun buildClasses(students: List<StudentDto>, classesNumber: Int): ClassroomsDto {
        logger.debug("Building classes for ${students.size} students, classes number: $classesNumber")
        val studentsList = studentTransformer.toStudentsList(students)
        val classes = classroomsEngine.buildClasses(studentsList, classesNumber)
        logger.debug("Finished building classes. summary: {}", classes.classesSummaries)
        return classroomsTransformer.toClassroomsDto(classes)
    }

}
