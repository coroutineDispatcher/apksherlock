import fileanalyser.*
import java.io.*

object APKSherlockInjector {
    private fun fileAnalyserRepository(dir: File) = FileAnalyserRepository(dir)
    fun fileAnalyserState(dir: File) = JavaFileAnalyserState(fileAnalyserRepository(dir))
}
