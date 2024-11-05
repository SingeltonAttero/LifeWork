package ya.weber.lifework.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ya.weber.lifework.presentation.state.GameOfLiveState
import ya.weber.lifework.presentation.state.LifeBox
import ya.weber.lifework.presentation.theme.LifeWorkTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeWorkTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.DarkGray)
                            .fillMaxSize()
                    ) {
                        WorldContainer(
                            modifier = Modifier,
                            viewModel = viewModel
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Spacer(modifier = Modifier.size(10.dp))
                                Button(onClick = {
                                    viewModel.start()
                                }) {
                                    Text("Start")
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Button(onClick = {
                                    viewModel.pause()
                                }) {
                                    Text("Pause")
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Button(onClick = {
                                    viewModel.addRandom()
                                }) {
                                    Text("Add 5 random")
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorldContainer(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel
) {
    val state by viewModel.state.collectAsState(GameOfLiveState.idle())
    if (state.grid == 0) return
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp / state.grid
    Column(
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxWidth()

    ) {
        Log.e("TEst", state.tiles.toString())
        state.tiles.forEach { row ->
            Row(
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()

            ) {
                row.forEach { item ->
                    WorldTile(
                        atom = item,
                        modifier = Modifier
                            .height(screenWidth.dp)
                            .width(screenWidth.dp)
                            .weight(1F)
                    )
                }

            }
        }
    }

}

@Composable
fun WorldTile(atom: LifeBox, modifier: Modifier = Modifier) {
    val current: Context = LocalContext.current
    Box(
        modifier = modifier
            .size(10.dp)
            .background(if (atom.isAlive) Color.Red else Color.Cyan),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LifeWorkTheme {

    }
}