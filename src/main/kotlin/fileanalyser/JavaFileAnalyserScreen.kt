package fileanalyser

import APKSherlockInjector
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

@Composable
fun JavaFileAnalyserScreen(
    modifier: Modifier = Modifier,
    pickedDirectory: File,
    onError: () -> Unit,
) {
    val state = remember(pickedDirectory) { APKSherlockInjector.fileAnalyserStateManager(pickedDirectory) }
    val javaAnalyserState = state.state.collectAsState()
    val horizontalScroll = rememberScrollState(0)

    Column(modifier = modifier.fillMaxSize()) {
        when (val currentState = javaAnalyserState.value) {
            is JavaFileAnalyserStateManagerManager.State.Success -> {
                LazyColumn(
                    modifier =
                        Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .horizontalScroll(horizontalScroll),
                ) {
                    currentState.data.forEach { analyzingFile ->
                        item {
                            Text(
                                analyzingFile.name,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 32.sp,
                            )
                        }

                        items(analyzingFile.lines) { line ->
                            Text(
                                line.value,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                modifier =
                                    Modifier.let { currentModifier ->
                                        if (line.matchesPatterns) {
                                            currentModifier.padding(2.dp).background(color = Color.Blue)
                                        } else {
                                            currentModifier
                                        }
                                    },
                            )
                        }

                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }
            }

            is JavaFileAnalyserStateManagerManager.State.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize().padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "Searching:",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                    )

                    Text(
                        "${currentState.fileName}...",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                    )
                }
            }

            JavaFileAnalyserStateManagerManager.State.Failure -> {
                Column(
                    modifier = modifier.fillMaxSize().padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        { onError() },
                        colors =
                            ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                backgroundColor = Color.Yellow,
                            ),
                    ) {
                        Text("Retry", color = Color.Black)
                    }
                    Text("Something went wrong or no Java files in this directory. Try again.", color = Color.White)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            state.clear()
        }
    }
}
