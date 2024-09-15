import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import fileanalyser.JavaFileAnalyserScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

fun main() =
    application {
        val applicationState = remember { ApplicationState() }
        val screensState = applicationState.state.collectAsState()

        val startWindowState = rememberWindowState(size = DpSize(600.dp, 600.dp))
        val mainAppWindowState = rememberWindowState(size = DpSize.Unspecified)

        when (val latestState = screensState.value) {
            ApplicationState.Screens.JavaFolderPicker -> {
                Window(onCloseRequest = ::exitApplication, title = "ApkSherlock", state = startWindowState) {
                    Box(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)) {
                        JavaFolderPickerScreen { pickedDirectory ->
                            applicationState.update(ApplicationState.Screens.JavaCodeAnalyser(pickedDirectory))
                        }
                    }
                }
            }

            is ApplicationState.Screens.JavaCodeAnalyser -> {
                Window(
                    onCloseRequest = { applicationState.update(ApplicationState.Screens.JavaFolderPicker) },
                    title = "${latestState.directory}",
                    state = mainAppWindowState,
                    // TODO add Find in Page feature
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)) {
                        JavaFileAnalyserScreen(pickedDirectory = latestState.directory, onError = {
                            applicationState.update(ApplicationState.Screens.JavaFolderPicker)
                        })
                    }
                }
            }
        }
    }

class ApplicationState {
    private val _state = MutableStateFlow<Screens>(Screens.JavaFolderPicker)
    val state = _state.asStateFlow()

    sealed class Screens {
        data object JavaFolderPicker : Screens()

        data class JavaCodeAnalyser(val directory: File) : Screens()
    }

    fun update(newState: Screens) {
        _state.update { newState }
    }
}
