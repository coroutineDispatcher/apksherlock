
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.File
import javax.swing.JFileChooser

@Composable
fun JavaFolderPickerScreen(
    modifier: Modifier = Modifier,
    onFilesLoaded: (File) -> Unit,
) {
    var selectedDirectory by remember { mutableStateOf<File?>(null) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Select the Java directory to analyse", color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val directory = pickDirectory()
                if (directory != null) {
                    selectedDirectory = directory
                    onFilesLoaded(directory)
                }
            },
            colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = Color.Yellow),
        ) {
            Text("Pick Directory")
        }
    }
}

fun pickDirectory(): File? {
    val chooser = JFileChooser()
    chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    val result = chooser.showOpenDialog(null)
    return if (result == JFileChooser.APPROVE_OPTION) {
        chooser.selectedFile
    } else {
        null
    }
}
