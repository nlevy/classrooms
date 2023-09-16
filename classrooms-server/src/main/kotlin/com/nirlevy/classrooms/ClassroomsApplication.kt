package com.nirlevy.classrooms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class ClassroomsApplication

fun main(args: Array<String>) {
	runApplication<ClassroomsApplication>(*args)
}

@Configuration
class BeanConfig {

	@Bean
	fun classroomsEngine(): ClassroomsEngine {
		return ClassroomsEngine()
	}
}