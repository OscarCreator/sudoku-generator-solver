import org.junit.jupiter.api.Test

/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuTest {

    val sudokuParser = SudokuFileParser("src/test/resources/puzzles0_kaggle", 2)

    @Test
    fun test_sudokuInsert(){
        sudokuParser.forEachSudoku {
            val sudoku = Sudoku(it)
            sudoku.toString() shouldBe it.replace(".", "0")
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

}


