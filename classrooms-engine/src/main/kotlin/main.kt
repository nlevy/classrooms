import com.nirlevy.classrooms.data.Gender
import com.nirlevy.classrooms.data.Grade
import com.nirlevy.classrooms.data.Student
import com.nirlevy.classrooms.evaluators.*
import com.nirlevy.genetic.GeneticProgramSolver
import com.nirlevy.genetic.GeneticSolver
import com.nirlevy.genetic.services.*
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
            getRandomFriends(it, 4),
            if (it % 30 == 0) getRandomBlock(it) else emptyList()
        )
    }.collect(Collectors.toList())

    val solver = GeneticProgramSolver(
        groupEvaluators = listOf(
            PreferredFriendsEvaluator(),
            GenderBalanceEvaluator(),
            SizeBalanceEvaluator(),
            AcademicPerformanceEvaluator(),
            BehavioralPerformanceEvaluator(),
            SeparateFromEvaluator()
        ), populationSize =  10000
    )
//    val solution = solver.solve(students, 6)

    val groupsUtils = GroupsUtils()
    val chromosomeEvaluator = ChromosomeEvaluator(
        groupsUtils,
        PreferredFriendsEvaluator(),
        GenderBalanceEvaluator(),
        SizeBalanceEvaluator(),
        AcademicPerformanceEvaluator(),
        BehavioralPerformanceEvaluator(),
        SeparateFromEvaluator()
    )
    val offspringProducer = OffspringProducer(chromosomeEvaluator)
    val chromosomeGenerator = ChromosomeGenerator(chromosomeEvaluator)
    val populationGenerator = PopulationGenerator(chromosomeGenerator)
    val gSolver = GeneticSolver(offspringProducer, populationGenerator, groupsUtils)

    val chromosome = gSolver.runSolver(students, 6)
    val createMap = createMap(chromosome.genes, students)
    printSolution(students, createMap)
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

private fun <T> createMap(groups: List<Int>, genes: List<T>): Map<Int, List<T>> {
    val map = HashMap<Int, MutableList<T>>()
    for ((index, gene) in genes.withIndex()) {
        map.computeIfAbsent(groups[index]) { ArrayList() }.add(gene)
    }
    return map
}
