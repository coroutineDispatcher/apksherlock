import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import java.io.*
import javax.swing.JFileChooser

@Composable
fun JavaFolderPickerScreen(modifier: Modifier = Modifier, onFilesLoaded: (File) -> Unit) {
    var selectedDirectory by remember { mutableStateOf<File?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            }, colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = Color.Yellow)
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
