/**
 * Created by Oscar on 2020-08-28.
 */
data class Cell(val column: Int, val row: Int, var clues: MutableList<Int>){

    fun clone(): Cell{
        return Cell(column, row, clues.toMutableList())
    }
}
