import org.junit.jupiter.api.Test

/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuTest {

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


