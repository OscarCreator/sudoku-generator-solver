import java.io.File

/**
 * Created by Oscar on 2020-08-27.
 */
class SudokuFileParser(private val fileName: String, private val startOffset: Int) {



    fun forEachSudoku(num: Long = Long.MAX_VALUE - startOffset.toLong(), function: (sudokuString: String) -> Unit){
        val bufferedReader = File(fileName).bufferedReader()
        var i = 0
        bufferedReader.lines().limit(num + startOffset).forEach {
            if (i >= startOffset){
                function(it)
            }else{
                i++
            }

        }
        bufferedReader.close()
    }


}