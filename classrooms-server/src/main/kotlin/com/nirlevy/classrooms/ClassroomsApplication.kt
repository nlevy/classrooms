package com.nirlevy.classrooms

import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class ClassroomsApplication

fun main(args: Array<String>) {
	runApplication<ClassroomsApplication>(*args)
}

@Configuration
class BeanConfig : WebMvcConfigurer {

	@Value("\${cors.origins.default}")
	private val defaultCorsOrigin: String = Strings.EMPTY

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins(defaultCorsOrigin)
			.allowedMethods(HttpMethod.GET.name, HttpMethod.POST.name)
			.allowedHeaders("*")
	}

	@Bean
	fun classroomsEngine(): ClassroomsEngine {
		return ClassroomsEngine()
	}
}