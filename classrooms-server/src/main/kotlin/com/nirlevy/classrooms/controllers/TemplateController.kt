package com.nirlevy.classrooms.controllers

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.model.StudentDto
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateController {


    companion object {
        val TEMPLATE = listOf(StudentDto("Name Example",
            "School Example",
            Gender.MALE,
            Grade.HIGH,
            Grade.MEDIUM,
            "optional comments",
            "First friend name",
            "Second friend name",
            "Third friend name",
            "Fourth friend name",
            "Student not to be with"
        ))
        private val HEADERS: HttpHeaders = HttpHeaders()
        init {
            HEADERS.contentType = MediaType.APPLICATION_JSON
        }
    }

    @CrossOrigin(origins = ["http://localhost:5173","localhost:5173"])// TODO move to somewhere central, make configurable
    @GetMapping("/template", produces = ["application/json"])
    fun getTemplate(): ResponseEntity<List<StudentDto>> {
        return ResponseEntity.ok()
            .headers(HEADERS)
            .body(TEMPLATE)
    }
}