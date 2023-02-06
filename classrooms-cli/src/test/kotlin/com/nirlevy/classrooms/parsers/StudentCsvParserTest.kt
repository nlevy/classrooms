package com.nirlevy.classrooms.parsers

import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StudentCsvParserTest {

    @Test
    internal fun parseCsv() {
        val reader = StudentCsvParser()
        val filename = javaClass.classLoader.getResource("testStudents.csv")?.toURI()?.path ?: fail("can't read file")
        val students = reader.parseStudents(filename)
        val byNameMap = students.associateBy { it.name }

        val john = byNameMap["John"]!!
        val ringo = byNameMap["Ringo"]!!
        val george = byNameMap["George"]!!
        val paul = byNameMap["Paul"]!!
        val yoko = byNameMap["Yoko"]!!
        
        assertSame(Gender.MALE, john.gender)
        assertSame(Grade.HIGH, john.academicPerformance)
        assertSame(Grade.HIGH, john.behavioralPerformance)
        assertEquals("AAA", john.school)
        assertEquals("bla bla", john.comments)
        assertTrue(john.cantBeWith.isEmpty())
        assertEquals(1, john.preferredFriends.size)
        assertTrue(john.preferredFriends.contains(ringo.id))

        assertSame(Gender.MALE, paul.gender)
        assertSame(Grade.HIGH, paul.academicPerformance)
        assertSame(Grade.HIGH, paul.behavioralPerformance)
        assertEquals("AAA", paul.school)
        assertEquals("bla bla", paul.comments)
        assertEquals(1, paul.cantBeWith.size)
        assertTrue(paul.cantBeWith.contains(john.id))
        assertEquals(1, paul.preferredFriends.size)
        assertTrue(paul.preferredFriends.contains(ringo.id))

        assertSame(Gender.MALE, ringo.gender)
        assertSame(Grade.LOW, ringo.academicPerformance)
        assertSame(Grade.HIGH, ringo.behavioralPerformance)
        assertEquals("AAA", ringo.school)
        assertEquals("bla bla", ringo.comments)
        assertTrue(ringo.cantBeWith.isEmpty())
        assertEquals(3, ringo.preferredFriends.size)
        assertTrue(ringo.preferredFriends.contains(john.id))
        assertTrue(ringo.preferredFriends.contains(george.id))
        assertTrue(ringo.preferredFriends.contains(paul.id))

        assertSame(Gender.MALE, george.gender)
        assertSame(Grade.HIGH, george.academicPerformance)
        assertSame(Grade.MEDIUM, george.behavioralPerformance)
        assertEquals("AAA", george.school)
        assertEquals("bla bla", george.comments)
        assertTrue(george.cantBeWith.isEmpty())
        assertEquals(2, george.preferredFriends.size)
        assertTrue(george.preferredFriends.contains(john.id))
        assertTrue(george.preferredFriends.contains(paul.id))

        assertSame(Gender.FEMALE, yoko.gender)
        assertSame(Grade.LOW, yoko.academicPerformance)
        assertSame(Grade.LOW, yoko.behavioralPerformance)
        assertEquals("AAA", yoko.school)
        assertEquals("witch", yoko.comments)
        assertEquals(1, yoko.cantBeWith.size)
        assertTrue(yoko.cantBeWith.contains(paul.id))
        assertTrue(yoko.preferredFriends.isEmpty())
    }
}
