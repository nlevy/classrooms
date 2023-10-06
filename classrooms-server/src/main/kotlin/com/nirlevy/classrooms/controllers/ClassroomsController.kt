package com.nirlevy.classrooms.controllers

import com.nirlevy.classrooms.model.ClassroomsDto
import com.nirlevy.classrooms.model.StudentDto
import com.nirlevy.classrooms.services.ClassroomsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ClassroomsController(private val classroomsService: ClassroomsService) {

    @PostMapping("/classrooms", consumes = ["application/json"], produces = ["application/json"])
    fun buildClassrooms(@RequestBody students: List<StudentDto>, @RequestParam classesNumber: Int) : ResponseEntity<ClassroomsDto> {
        val classes = classroomsService.buildClasses(students, classesNumber)
        return ResponseEntity.ok()
            .body(classes)
    }
}