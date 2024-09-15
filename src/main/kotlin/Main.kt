import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import fileanalyser.*
import kotlinx.coroutines.flow.*
import java.io.File

fun main() = application {
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
                state = mainAppWindowState
                // TODO add Find in Page feature
            ) {
                Box(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)) {
                    JavaFileAnalyser(pickedDirectory = latestState.directory, onError = {
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
