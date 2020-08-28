import java.io.File

/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuFileParser(private val fileName: String, private val startOffset: Int = 0) {



    fun forEachSudoku(num: Long = Long.MAX_VALUE - startOffset.toLong(), function: (sudokuString: String, solvedSudokuString: String) -> Unit){
        val bufferedReader = File(fileName).bufferedReader()
        var i = 0
        bufferedReader.lines().limit(num + startOffset).forEach {
            val unsolvedSudokuString = it.substring(0, 81)
            val solvedSudokuString = it.substring(84)
            if (i >= startOffset){
                function(unsolvedSudokuString, solvedSudokuString)
            }else{
                i++
            }

        }
        bufferedReader.close()
    }


}