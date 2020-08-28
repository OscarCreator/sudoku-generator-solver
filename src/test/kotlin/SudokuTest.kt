import org.junit.jupiter.api.Test

/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuTest {

    val sudokuParser = SudokuFileParser("src/test/resources/test_puzzles")

    val solver = SudokuSolver()

    @Test
    fun test_sudokuInsert(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.toString() shouldBe unsolved.replace(".", "0")
        }
    }

    @Test
    fun test_emptySudokuIsFilledWithZeros(){
        val sudoku = Sudoku()
        var string = ""
        for (i in 1..81){
            string += "0"
        }
        sudoku.toString() shouldBe string
    }

    @Test
    fun test_getValue(){
        sudokuParser.forEachSudoku(18) { unsolved: String, solved: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.getValue(0,0) shouldBe Character.getNumericValue(unsolved.replace(".", "0")[0])
        }
    }

    @Test
    fun test_setCell(){
        sudokuParser.forEachSudoku(18) { unsolved: String, solved: String ->
            val sudoku = Sudoku(unsolved)
            val replacedValueSudoku = "1${unsolved.substring(1)}"
            sudoku.setCell(0,0, 1)
            sudoku.toString() shouldBe replacedValueSudoku.replace(".", "0")
        }
    }

    @Test
    fun test_calculateClues(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.getRowNumbers(0) shouldBe arrayListOf(5,8,3,1,7)

        }
    }


    @Test
    fun test_cluesCalculatedWithEmptySudoku(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku()
            sudoku.calculateClues()
            sudoku.getClues().forEach {
                it.clues.size shouldBe 9
                it.clues shouldBe mutableListOf(1,2,3,4,5,6,7,8,9)
            }


        }
    }

    @Test
    fun test_calculatedClues(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.calculateClues()
            sudoku.getClues().forEach {
                if (sudoku.getValue(it.column, it.row) != 0){
                    //With value has no clues
                    it.clues.size shouldBe 0
                }else{
                    //No value has at least one clue
                    assert(it.clues.size > 0)
                }
            }
        }
    }

    @Test
    fun test_getBoxNumbers(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.getBoxNumbers(0) shouldBe arrayListOf(5,3,4)

        }
    }

    @Test
    fun test_getColumnNumbers(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.getColumnNumbers(0) shouldBe arrayListOf(3,1)

        }
    }

    @Test
    fun test_getRowNumbers(){
        sudokuParser.forEachSudoku(1) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.getRowNumbers(0) shouldBe arrayListOf(5,8,3,1,7)

        }
    }

    @Test
    fun test_sudokuNotCompleted(){
        sudokuParser.forEachSudoku(18) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.complete() shouldBe false

        }
    }

    @Test
    fun test_sudokuCompleted(){
        sudokuParser.forEachSudoku(18) { _: String, solved: String ->
            val sudoku = Sudoku(solved)
            sudoku.complete() shouldBe true

        }
    }

    @Test
    fun test_unsolvedSudokuValid(){
        sudokuParser.forEachSudoku(18) { unsolved: String, _: String ->
            val sudoku = Sudoku(unsolved)
            sudoku.isValid() shouldBe true

        }
    }

    @Test
    fun test_solvedSudokuValid(){
        sudokuParser.forEachSudoku(18) { _: String, solved: String ->
            val sudoku = Sudoku(solved)
            sudoku.isValid() shouldBe true

        }
    }


    @Test
    fun test_solveFirstSudoku(){
        sudokuParser.forEachSudoku(18) { unsolved: String, solved: String ->
            val sudoku = Sudoku(unsolved)
            val solvedSudoku = sudoku.clone()
            solver.solveFirstSolution(solvedSudoku)
            println("solved sudoku: $solvedSudoku")
            solvedSudoku.isValid() shouldBe true
            solvedSudoku.complete() shouldBe true
            solvedSudoku.toString() shouldBe solved


        }
    }

}


