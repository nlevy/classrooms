import com.nirlevy.classrooms.evaluators.*
import com.nirlevy.classrooms.parsers.StudentCsvParser
import com.nirlevy.classrooms.parsers.StudentsTransformer
import com.nirlevy.genetic.GeneticSolver
import java.nio.file.Paths


fun main() {

    val parser = StudentCsvParser(StudentsTransformer())
    val filename = Paths.get("classrooms-cli/src/test/resources/largeTestFile.csv").toAbsolutePath().toString()
    val students = parser.parseStudents(filename)

    val solver = GeneticSolver(
        listOf(PreferredFriendsEvaluator(),
        GenderBalanceEvaluator(),
        SizeBalanceEvaluator(),
        AcademicPerformanceEvaluator(),
        BehavioralPerformanceEvaluator(),
        SeparateFromEvaluator()), emptyList()
    )

    val solution = solver.solve(students, 6)
    printSolution(students, solution)

//    parser.writeToCsv("/tmp/testme.csv", solution)
}
