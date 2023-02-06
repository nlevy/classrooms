import com.nirlevy.classrooms.evaluators.*
import com.nirlevy.classrooms.parsers.StudentCsvParser
import com.nirlevy.genetic.GeneticProgramSolver
import java.nio.file.Paths


fun main() {

    val parser = StudentCsvParser()
    val filename = Paths.get("classrooms-cli/src/test/resources/largeTestFile.csv").toAbsolutePath().toString()
    val students = parser.parseStudents(filename)

    val solver = GeneticProgramSolver(
        groupEvaluators = listOf(
            PreferredFriendsEvaluator(),
            GenderBalanceEvaluator(),
            SizeBalanceEvaluator(),
            AcademicPerformanceEvaluator(),
            BehavioralPerformanceEvaluator(),
            SeparateFromEvaluator()
        ), populationSize = 10000
    )
    val solution = solver.solve(students, 6)
    printSolution(students, solution)

}
