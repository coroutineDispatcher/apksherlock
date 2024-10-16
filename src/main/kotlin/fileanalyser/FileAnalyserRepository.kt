package fileanalyser

import data.AnalyzingFile
import data.FilesCollection
import data.Loading
import data.toLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File

class FileAnalyserRepository(private val chosenDir: File) {
    private val data = mutableMapOf<String, List<String>>()

    fun readAllTheFiles() =
        flow {
            suspend fun openFiles(dir: File): List<AnalyzingFile> {
                emit(Loading(dir.name))

                if (dir.isFile && dir.name.endsWith(".java")) {
                    val lines = withContext(Dispatchers.IO) { dir.readLines() }
                    data[dir.name] = lines
                }

                if (dir.isDirectory) {
                    dir.listFiles()?.let {
                        it.forEach { file ->
                            openFiles(file)
                        }
                    }
                }

                return data.map { capturedFilesMap ->
                    AnalyzingFile(
                        capturedFilesMap.key,
                        capturedFilesMap.value.map { it.toLine() },
                    )
                }
            }

            val data = openFiles(chosenDir)
            emit(FilesCollection(data))
        }
}
