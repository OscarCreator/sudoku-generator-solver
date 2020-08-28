import java.lang.Exception

/**
 * Created by Oscar on 2020-08-27.
 */
class Sudoku(private val sudokuString: String = "") {

    private val grid: MutableList<Int> = MutableList(81){ 0 }

    private val cluesCache: Array<Cell> = Array(81) {
        Cell(it % 9, it / 9, mutableListOf())
    }

    private val tempList = arrayListOf<Int>()

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

        calculateClues()

    }

    fun getValue(column: Int, row: Int): Int{
        validateRowAndColumn(column)
        validateRowAndColumn(row)
        return grid[getIndex(column, row)]
    }

    fun setCell(column: Int, row: Int, value: Int) {
        val currentIndex = getIndex(column, row)
        setCell(currentIndex, value)
    }

    fun setCell(index: Int, value: Int) {
        grid[index] = value
        //clear all clues from this cell
        cluesCache[index].clues.clear()
        val cells = getCellsWhichIsAffectedBy(index)
        for (cell in cells){
            //update clues to the new ones
            cell.clues.remove(value)
            println(cell)
        }
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

    fun printSudoku(extra: String = ""){
        val stringBuilder = StringBuilder()
        stringBuilder.append(extra).append("\n")
        for (i in 0 until 9){
            for (j in 0 until 9){
                stringBuilder.append(grid[j + i * 9])
            }
            stringBuilder.append("\n")
        }
        println(stringBuilder.toString())
    }

    fun calculateClues() {
        //For each
        for (i in 0 until 81){
            //If cell is empty
            if (grid[i] == 0){
                val columnNumbers = getColumnNumbers(i)
                val rowNumbers = getRowNumbers(i)
                val boxNumbers = getBoxNumbers(i)
                val availableNumbers: Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9)
                for (columnValue in columnNumbers){
                    availableNumbers[columnValue - 1] = 0
                }
                for (rowValue in rowNumbers){
                    availableNumbers[rowValue - 1] = 0
                }
                for (boxValue in boxNumbers){
                    availableNumbers[boxValue - 1] = 0
                }

                cluesCache[i].clues = availableNumbers.filter { it != 0 }.toMutableList()
            }
        }
    }

    fun getBoxNumbers(index: Int): ArrayList<Int> {
        val list = arrayListOf<Int>()
        val boxColumn = index % 9 / 3
        val boxRow = index / 27

        for (i in 0 until 9){
            val currentIndex = getIndex(boxColumn * 3 + i % 3, boxRow * 3 + i / 3)
            val gridnumber = grid[currentIndex]
            if (gridnumber != 0){
                list.add(gridnumber)
            }
        }
        return list
    }

    fun getRowNumbers(index: Int): ArrayList<Int> {
        val list = arrayListOf<Int>()
        val startIndex = index - index % 9
        for (i in 0 until 9){
            val gridnumber = grid[i + startIndex]
            if (gridnumber != 0){
                list.add(gridnumber)
            }

        }
        return list
    }

    fun getColumnNumbers(index: Int): ArrayList<Int> {
        val list = arrayListOf<Int>()
        val startIndex = index % 9
        for (i in 0 until 9){
            val gridnumber = grid[i * 9 + startIndex]
            if (gridnumber != 0){
                list.add(gridnumber)
            }

        }
        return list
    }

    fun isValid(): Boolean {

        for (i in 0 until 9){
            if (!isBoxValid(i)){
                return false
            }
            if (!isColumnValid(i)){
                return false
            }
            if (!isRowValid(i)){
                return false
            }
        }
        return true
    }

    private fun isColumnValid(index: Int): Boolean {
        tempList.clear()
        for (i in 0 until 9){
            val currentIndex = i * 9 + index
            val gridnumber = grid[currentIndex]
            if (gridnumber != 0){
                if (tempList.contains(gridnumber)){
                    return false
                }
                tempList.add(gridnumber)
            }else if (cluesCache[currentIndex].clues.size == 0){
                return false
            }

        }
        return true
    }

    private fun isRowValid(index: Int): Boolean {
        tempList.clear()
        for (i in 0 until 9){
            val currentIndex = i + index * 9
            val gridnumber = grid[currentIndex]
            if (gridnumber != 0){
                if (tempList.contains(gridnumber)){
                    return false
                }
                tempList.add(gridnumber)
            }else if (cluesCache[currentIndex].clues.size == 0){
                return false
            }

        }
        return true
    }

    private fun isBoxValid(index: Int): Boolean {
        tempList.clear()
        val boxColumn = index % 9 / 3
        val boxRow = index / 27

        for (i in 0 until 9){
            val currentIndex = getIndex(boxColumn * 3 + i % 3, boxRow * 3 + i / 3)
            val gridnumber = grid[currentIndex]
            if (gridnumber != 0){
                if (tempList.contains(gridnumber)){
                    return false
                }

                tempList.add(gridnumber)
            }else if (cluesCache[currentIndex].clues.size == 0){
                return false
            }

        }
        return true
    }

    fun singlesExists(): Boolean {
        for (i in 0 until 81){
            if (cluesCache[i].clues.size == 1 && grid[i] == 0){
                return true
            }
        }
        return false
    }

    fun getClues(): Array<Cell>{
        return cluesCache.clone()
    }

    fun fillSinglesAndRemoveClues() {
        for (i in 0 until 81){

            val currentClues = cluesCache[i].clues
            if (currentClues.size == 1){
                println("affected by column:${i % 9}, row: ${i / 9}, value: ${currentClues[0]}")
                //fill number
                setCell(i, currentClues[0])
            }
        }
    }

    fun complete(): Boolean {
        for (i in 0 until 81){
            if (grid[i] == 0){
                return false
            }
        }
        return true
    }

    fun getGoodGuess(): Cell {
        var bestGuess: Cell = cluesCache[0]
        for (i in 1 until 81){
            var bestClueCount: Int = bestGuess.clues.size
            if (bestGuess.clues.size == 0){
                bestClueCount = 10
            }
            if (cluesCache[i].clues.size in 1 until bestClueCount){
                bestGuess = cluesCache[i]
            }
        }
        return bestGuess.clone()
    }

    private fun getCellsWhichIsAffectedBy(index: Int): ArrayList<Cell>{
        val cellList = arrayListOf<Cell>()
        val column = index % 9
        val row = index / 9

        for (i in 0 until 9){
            val gridIndex = i + row * 9
            if (grid[gridIndex] == 0){
                cellList.add(cluesCache[gridIndex])
            }
        }

        for (i in 0 until 9){
            val gridIndex = i * 9 + column
            if (gridIndex / 9 != row) {
                if (grid[gridIndex] == 0){
                    cellList.add(cluesCache[gridIndex])
                }
            }

        }

        val boxX = column / 3
        val boxY = row / 3

        for (i in 0 until 9){
            val currentIndex = getIndex(boxX * 3 + i % 3, boxY * 3 + (i / 3))
            val currentRow = currentIndex / 9
            val currentColumn = currentIndex % 9
            //If cell has a number
            if (grid[currentIndex] == 0){
                if (currentRow != row && currentColumn != column){
                    cellList.add(cluesCache[currentIndex])
                }

            }
        }
        return cellList
    }


    fun clone(): Sudoku {
        return Sudoku(sudokuString)
    }

    fun printClueCache(i: Int, j: Int) {
        println(cluesCache[i + j * 9].hashCode())
    }


    class WrongStringLengthException(message: String) : Exception(message)

}