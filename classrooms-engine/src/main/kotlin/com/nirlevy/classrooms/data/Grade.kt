package com.nirlevy.classrooms.data

enum class Grade(val value: Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    companion object {
        fun fromValue(value: Int) = when (value) {
            1-> LOW
            2-> MEDIUM
            3-> HIGH
            else -> throw IllegalArgumentException("no such Grade value $value")
        }
    }
}