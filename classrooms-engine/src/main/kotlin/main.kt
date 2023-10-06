import com.nirlevy.classrooms.ClassroomsEngine
import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import java.lang.Integer.min
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.random.Random

fun main() {
    val totalStudents = 240
    val students = IntStream.range(0, totalStudents).boxed().map {
        val gender = if (Random.nextInt(1,3) % 2 == 0) Gender.FEMALE else Gender.MALE

        Student(it,
            gender,
            Grade.fromValue(Random.nextInt(1, 4)),
            Grade.fromValue(Random.nextInt(1, 4)),
            0,
            getRandomFriends(it, 4),
            if (it % 30 == 0) getRandomBlock(it) else emptyList()
        )
    }.collect(Collectors.toList())

    val classroomsEngine = ClassroomsEngine()

    val solution = classroomsEngine.buildClasses(students, 6)
    printSolution(students, solution.classes)
}

fun getRandomBlock(id: Int): List<Int> {
    val lower = min(1, id - 20)
    val upper = lower + 40
    return listOf(Random.nextInt(upper - lower) + lower, Random.nextInt(upper - lower) + lower)
}

fun getRandomFriends(i: Int, size: Long): List<Int> {
    val lower = (i / 20) * 20 + 1
    val upper = lower + 19

    return IntStream.generate {
        var n = Random.nextInt(upper - lower) + lower
        while (n == i) n = Random.nextInt(upper - lower) + lower
        n
    }.limit(size).boxed().collect(Collectors.toList())
}

fun printSolution(students: List<Student>, byClassMap: Map<Int, List<Student>>) {
    byClassMap.forEach { (i, c) ->
        println("Class $i")
        println("number of students: ${c.size}")
        println("number of males: " + c.count { it.gender== Gender.MALE })
        println("avg academicPerformance: " + c.sumOf { it.academicPerformance.value } / c.size.toDouble())
        println("avg behavioralPerformance: " + c.sumOf { it.behavioralPerformance.value } / c.size.toDouble())
        val friendsCount = getFriendsCount(c)
        val allValid = friendsCount.all{ it > 0}
        println("preferred friends count: $friendsCount (allValid=$allValid)" )
        val studentIds = c.map { it.id }.toSet()
        println("disallowed matches count: ${c.count { student -> student.cantBeWith.any { it in studentIds } }}")
    }
    println()
    println(" Total male count: ${byClassMap.values.flatten().count{it.gender== Gender.MALE }}")
    println(" Total avg academicPerformance: ${byClassMap.values.flatten().sumOf { it.academicPerformance.value } / students.size.toDouble()}")
    println(" Total avg behavioralPerformance: ${byClassMap.values.flatten().sumOf { it.behavioralPerformance.value } / students.size.toDouble()}")
}

fun getFriendsCount(classroom: List<Student>) : List<Int> {
    return classroom.map {
        it.preferredFriends.intersect(classroom.map(Student::id).toSet()).size
    }
}
