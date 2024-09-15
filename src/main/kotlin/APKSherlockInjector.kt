import fileanalyser.FileAnalyserRepository
import fileanalyser.JavaFileAnalyserState
import java.io.File

object APKSherlockInjector {
    private fun fileAnalyserRepository(dir: File) = FileAnalyserRepository(dir)

    fun fileAnalyserState(dir: File) = JavaFileAnalyserState(fileAnalyserRepository(dir))
}
