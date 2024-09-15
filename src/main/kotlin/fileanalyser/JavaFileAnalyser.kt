package fileanalyser

import APKSherlockInjector
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import java.io.File

@Composable
fun JavaFileAnalyser(modifier: Modifier = Modifier, pickedDirectory: File, onError: () -> Unit) {

    val state = remember(pickedDirectory) { APKSherlockInjector.fileAnalyserState(pickedDirectory) }
    val javaAnalyserState = state.state.collectAsState()
    val horizontalScroll = rememberScrollState(0)

    DisposableEffect(Unit) {
        onDispose {
            state.clear()
        }
    }

    when (val currentState = javaAnalyserState.value) {
        is JavaFileAnalyserState.State.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .horizontalScroll(horizontalScroll)
            ) {
                currentState.data.forEach { analyzingFile ->
                    item {
                        Text(
                            analyzingFile.name,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 32.sp
                        )
                    }

                    items(analyzingFile.lines) { line ->
                        Text(
                            line.value,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.let { currentModifier ->
                                if (line.shouldHighlight)
                                    currentModifier.padding(2.dp).background(color = Color.Blue)
                                else currentModifier
                            }
                        )
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }

        is JavaFileAnalyserState.State.Loading -> {
            Column(
                modifier = modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "${currentState.fileName}...",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                )
            }
        }

        JavaFileAnalyserState.State.Failure -> {
            Column(
                modifier = modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    { onError() },
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = Color.Yellow)
                ) {
                    Text("Retry", color = Color.Black)
                }
                Text("Something went wrong or no Java files in this directory. Try again.", color = Color.White)
            }
        }
    }
}
