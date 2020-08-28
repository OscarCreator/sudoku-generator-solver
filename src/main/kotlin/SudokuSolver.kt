/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuSolver {

    /**
     * Tries to solve the sudoku and returns all the valid sudokus.
     * @param sudoku the sudoku to solve
     * @return all solutions to the given sudoku
     * */
    fun solve(sudoku: Sudoku): List<Sudoku> {
        TODO()
    }

    /**
     * Tries to solve the sudoku and returns the first valid solution.
     * */
    fun solveFirstSolution(sudoku: Sudoku): Sudoku?{
        sudoku.calculateClues()
        sudoku.printSudoku("new depth: ")
        if (!sudoku.isValid()){
            println("not valid, back")
            return null
        }
        while (sudoku.singlesExists()){
            sudoku.fillSinglesAndRemoveClues()
            sudoku.printSudoku("singles exists: ")
        }
        if (!sudoku.complete()){
            val goodGuess: Cell = sudoku.getGoodGuess()
            for (clue in goodGuess.clues){
                sudoku.setCell(goodGuess.column, goodGuess.row, clue)
                println("good guess: $goodGuess")
                val returnedSudoku = solveFirstSolution(sudoku)
                if (returnedSudoku != null){
                    return returnedSudoku
                }
            }
            return null
        }
        return sudoku
    }

}