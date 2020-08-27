import java.lang.Exception

/**
 * Created by Oscar on 2020-08-27.
 */
class Sudoku(sudokuString: String = "") {

    private val grid: MutableList<Int> = MutableList(81){ 0 }

    init {
        if (sudokuString != ""){
            if (sudokuString.length != 81){
                throw WrongStringLengthException("Wrong length of string: expected a length of 81 but got: ${sudokuString.length}")
            }
            var i = 0;
            for (char in sudokuString){
                if (char != '.'){
                    grid[i] = Character.getNumericValue(char)
                }
                i++
            }
        }

    }

    fun getCell(column: Int, row: Int): Int{
        validateRowAndColumn(column)
        validateRowAndColumn(row)
        return grid[getIndex(column, row)]
    }

    fun setCell(column: Int, row: Int, value: Int) {
        grid[getIndex(column, row)] = value
    }

    private fun getIndex(column: Int, row: Int): Int {
        return row * 9 + column
    }

    private fun validateRowAndColumn(value: Int){
        if (value !in 0..9){
            throw ArrayIndexOutOfBoundsException("Value out of index: expected value between 1-9 but got $value.")
        }
    }

    override fun toString(): String {
        return grid.joinToString ("")
    }

    class WrongStringLengthException(message: String) : Exception(message){

    }

}