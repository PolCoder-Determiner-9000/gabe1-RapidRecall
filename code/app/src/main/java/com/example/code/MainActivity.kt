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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.code.ui.theme.CodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gameState = GameState()
        val summaryRepo = Summary()
        setContent {
            CodeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /* Screen Switching via Enumerations
                    * Since I Couldn't learn Navhost on time, resort to enums to switch between screens
                    * Use a simple When (Case/switch) Statement to change between screens
                    * Use lambdas from functions to change enum
                    */
                    var screen by rememberSaveable { mutableStateOf(ScreenMode.ENTRANCE) }

                    when (screen) {
                        ScreenMode.ENTRANCE -> EntranceScreen(
                            modifier = Modifier.padding(innerPadding),
                            onChange = { mode: ScreenMode -> screen = mode },
                            clearSequence = gameState::clearSequence,
                            increaseRound = gameState::increaseRound,
                        )
                        ScreenMode.LOG -> LogScreen(
                            gameState  = gameState,
                            modifier   = Modifier.padding(innerPadding),
                            onEntrance = { screen = ScreenMode.ENTRANCE },
                            summary    = summaryRepo
                        )
                        ScreenMode.START -> SetupScreen(
                            roundNumber  = gameState.getRound(),
                            onBeginRound = { screen = ScreenMode.DISPLAY },
                            modifier     = Modifier.padding(innerPadding),
                            // Pass Class Methods into a function in order for function to use
                            initializeSequence = gameState::initializeSequence,

                        )
                        ScreenMode.DISPLAY -> DisplayNumbers(
                            sequence   = gameState.gameGetSequence().getIntList(),
                            roundNum   = gameState.getRound(),
                            onFinished = { screen = ScreenMode.GUESS },
                            modifier   = Modifier.padding(innerPadding)
                        )
                        ScreenMode.GUESS -> GuessNumberScreen(
                            onDone   = { screen = ScreenMode.SUMMARY },
                            modifier = Modifier.padding(innerPadding),
                            answer = gameState.gameGetSequence(),
                            summary  = summaryRepo,
                            roundNumber = gameState.getRound()
                        )
                        ScreenMode.SUMMARY -> SummaryScreen(
                            summary  = summaryRepo,
                            roundNum = gameState.getRound(),
                            answer   = gameState.gameGetSequence(),
                            onPlayAgain = { screen = ScreenMode.ENTRANCE },
                            modifier    = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

/* Entrance Screen
* MAIN STARTING POINT of the program, switch between
* Parameters:
*   Modifier: Modifier
*   onChange: Lambda to change enum input into desired screen
*   (Like lambda changing it to one screen, but we're allowing 3 options in the code now)
*   increaseRound: Increase Round whenever I wanna start a new round
*   clearSequence: Clear Sequence when round Starts
* Returns:
*   Unit
*   A Screen
*/
@Composable
fun EntranceScreen(
    modifier: Modifier = Modifier,
    onChange: (ScreenMode) -> Unit,
    increaseRound: () -> Unit,
    clearSequence: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "gabe1 Rapid Recall",
            fontSize = 42.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .width(160.dp)
                .fillMaxWidth(0.7f)
                .height(64.dp),
            onClick = {
                clearSequence()
                increaseRound()
                onChange( ScreenMode.START )
            }
        ) {
            Text("Start", style = MaterialTheme.typography.titleLarge)
        }

        Button(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .width(160.dp)
                .fillMaxWidth(0.7f)
                .height(64.dp),
            onClick = {
                onChange( ScreenMode.LOG )
            }
        ) {
            Text("Log", style = MaterialTheme.typography.titleLarge)
        }

        Button(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .width(160.dp)
                .fillMaxWidth(0.7f)
                .height(64.dp),
            onClick = {
                onChange( ScreenMode.SUMMARY )
            }
        ) {
            // Start Display Numbers
            Text("Summary", style = MaterialTheme.typography.titleLarge)
        }
    }
}

/* SetupScreen
* Main Screen that handles initializing the round
* Parameters:
*   roundNumber: Round Number Integer
*   initializeSequence: Initialize the Sequence (Method from GameState)
*   onBeginRound: Lambda to switch screenMode Enum to display round
*   modifier: Modifier for padding
* Returns:
*   A Screen
*/
@Composable
fun SetupScreen(
    roundNumber: Int,
    initializeSequence: (Int) -> Unit,
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
            text = "Round $roundNumber",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter Length (from 1-10)",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        Spacer(Modifier.weight(1f))

        // Incrementing Button: See Citation [4]
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Buttons are rounded by size
            Button(
                onClick = { length = (length - 1).coerceAtLeast(1) },
                enabled = length > 1,
                modifier = Modifier.size(64.dp),
            ) {
                Text("<", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                text = length.toString(),
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.width(96.dp)
            )

            Button(
                onClick = { length = (length + 1).coerceAtMost(10) },
                enabled = length < 10,
                modifier = Modifier.size(64.dp),
            ) {
                Text(">", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
                .height(64.dp)
                .width(216.dp)
                .fillMaxWidth(0.6f),
            onClick = {
                initializeSequence(length)
                onBeginRound()
            }
        ) {
            // Start Display Numbers
            Text("Begin Round", style = MaterialTheme.typography.titleLarge)
        }
    }
}

