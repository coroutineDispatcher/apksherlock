package fileanalyser

import BaseStateManager
import data.AnalyzingFile
import data.FilesCollection
import data.Loading
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class JavaFileAnalyserStateManagerManager(
    repository: FileAnalyserRepository,
) : BaseStateManager() {
    val state =
        repository.readAllTheFiles().map { result ->
            when (result) {
                is FilesCollection -> {
                    if (result.analyzingFiles.isEmpty()) {
                        State.Failure
                    } else {
                        State.Success(result.analyzingFiles)
                    }
                }

                is Loading -> State.Loading(result.fileName)
            }
        }.stateIn(baseScope, SharingStarted.Lazily, State.Loading(""))

    sealed class State {
        data class Loading(val fileName: String) : State()

        data class Success(val data: List<AnalyzingFile>) : State()

        data object Failure : State()
    }
}
