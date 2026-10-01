package com.example.code

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.code.ui.theme.CodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gameState = GameState()
        setContent {
            CodeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var screen by rememberSaveable { mutableStateOf(ScreenMode.SETUP) }

                    when (screen) {
                        ScreenMode.SETUP -> SetupScreen(
                            roundNumber = 1,
                            onBeginRound = { screen = ScreenMode.DISPLAY },
                            modifier = Modifier.padding(innerPadding)
                        )
                        ScreenMode.DISPLAY -> DisplayNumbers(
                            onFinished = { screen = ScreenMode.GUESS },
                            modifier = Modifier.padding(innerPadding)
                        )
                        ScreenMode.GUESS -> GuessNumberScreen(
                            onDone = { screen = ScreenMode.SUMMARY },
                            modifier = Modifier.padding(innerPadding)
                        )
                        ScreenMode.SUMMARY -> SummaryScreen(
                            onPlayAgain = { screen = ScreenMode.SETUP },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun SetupScreen(
    roundNumber: Int,
    onBeginRound: () -> Unit,
    modifier: Modifier = Modifier
) {
    var length by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Rapid Recall",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text( text = "Round $roundNumber")

        Spacer(modifier = Modifier.width(24.dp))

        Text(text = "Enter Length (from 1-10)")
        Spacer(modifier = Modifier.width(8.dp))

        // Increment button: https://claude.ai/chat/626991d3-a6eb-4988-a5a5-cd0724a7a22a
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { length = (length - 1).coerceAtLeast(1) },
                enabled = length > 1
            ) {
                Text("<")
            }

            Text(
                text = length.toString(),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(48.dp) // fixed width so the buttons don't shift at 10
            )

            Button(
                onClick = { length = (length + 1).coerceAtMost(10) },
                enabled = length < 10
            ) {
                Text(">")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.padding(vertical = 12.dp),
            onClick = {
                onBeginRound()
            }
        ) {
            // Start Display Numbers
            Text("Begin Round")
        }
    }
}

