package com.nirlevy.classrooms.services

import com.nirlevy.classrooms.ClassroomsEngine
import com.nirlevy.classrooms.model.ClassroomsDto
import com.nirlevy.classrooms.model.StudentDto
import com.nirlevy.classrooms.transformers.ClassroomsTransformer
import com.nirlevy.classrooms.transformers.StudentTransformer
import org.springframework.stereotype.Service

@Service
class ClassroomsService(
    private val classroomsTransformer: ClassroomsTransformer,
    private val studentTransformer: StudentTransformer,
    private val classroomsEngine: ClassroomsEngine
) {

    fun buildClasses(students: List<StudentDto>, classesNumber: Int): ClassroomsDto {
        val studentsList = studentTransformer.toStudentsList(students)
        val classes = classroomsEngine.buildClasses(studentsList, classesNumber)
        return classroomsTransformer.toClassroomsDto(classes)
    }

}
