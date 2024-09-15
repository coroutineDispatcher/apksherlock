import fileanalyser.FileAnalyserRepository
import fileanalyser.JavaFileAnalyserStateManagerManager
import java.io.File

object APKSherlockInjector {
    private fun fileAnalyserRepository(dir: File) = FileAnalyserRepository(dir)
    fun fileAnalyserStateManager(dir: File) = JavaFileAnalyserStateManagerManager(fileAnalyserRepository(dir))
}
