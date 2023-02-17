import com.nirlevy.classrooms.evaluators.*
import com.nirlevy.classrooms.parsers.StudentCsvParser
import com.nirlevy.genetic.GeneticSolver
import com.nirlevy.genetic.services.*
import java.nio.file.Paths


fun main() {

    val parser = StudentCsvParser()
    val filename = Paths.get("classrooms-cli/src/test/resources/largeTestFile.csv").toAbsolutePath().toString()
    val students = parser.parseStudents(filename)

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
    val solver = GeneticSolver(offspringProducer, populationGenerator, groupsUtils)

    val solution = solver.solve(students, 6)
    printSolution(students, solution)
}
