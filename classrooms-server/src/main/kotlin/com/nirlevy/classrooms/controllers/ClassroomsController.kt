package com.nirlevy.classrooms.controllers

import com.nirlevy.classrooms.model.StudentDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalStateException

@RestController
class ClassroomsController {

    @CrossOrigin(origins = ["http://localhost:5173","localhost:5173"])// TODO move to somewhere central, make configurable
    @PostMapping("/classrooms", consumes = ["application/json"], produces = ["application/json"])
    fun buildClassrooms(@RequestBody students: List<StudentDto>) : ResponseEntity<List<StudentDto>> {
        throw IllegalStateException("f")// TODO Implement
    }
}