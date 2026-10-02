package com.example.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.time.Duration
import kotlin.time.DurationUnit

/*
* Log Screen
* Display the History of the round, especially
* Round number, time, sequence, timestamp
* Parameters:
*   - gameState: GameState to access round information
*   - modifier: Modifier
*   - onEntrance: Lambda to return to Entrance
*   - Summary: Summary to show rounds
* Returns: Composable Screen
*/
@Composable
fun LogScreen(
    gameState: GameState,
    modifier: Modifier = Modifier,
    onEntrance: () -> Unit,
    summary: Summary
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Logged Rounds",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(64.dp))

            // Show null Sequence if no rounds to cache
            // Otherwise use lazy column to display previous rounds
            if (gameState.getLength() == 0) {
                Text("Round ${gameState.getRound()}", fontSize = 24.sp)
                Spacer(Modifier.height(8.dp))
                Text("Length: ${gameState.getLength()}", fontSize = 24.sp)
                Spacer(Modifier.height(8.dp))
                Text("Sequence: None", fontSize = 24.sp)
            } else {
                LazyColumn(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    val rounds: List<Round> = summary.getRounds().reversed()
                    items(rounds) { round ->
                        Text("Round ${round.roundNumber}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Length: ${round.length}", fontSize = 24.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Timestamp: ${"%.2f".format(round.time.toDouble(DurationUnit.SECONDS))}s", fontSize = 24.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Sequence: ", fontSize = 24.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(round.roundSequence.getIntList().joinToString(), fontSize = 24.sp)

                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 2.dp,
                            color = Color.Gray
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
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
                onEntrance()
            }
        ) {
            Text("Go Back", style = MaterialTheme.typography.titleLarge)
        }
    }
}

/*
* Summary Screen
* Display the statistics calculated by Summary
* Parameters:
*   - Summary: Summary
*   - roundNum: Round Number
*   - answer: Answer sequence of round
*   - Modifier: Modifier to pad stuff
*   - onPlayAgain: Lambda that returns to the beginning screen
* Returns: Composable Screen
*/
@Composable
fun SummaryScreen(
    summary      : Summary,
    roundNum     : Int,
    answer       : Sequence,
    modifier     : Modifier = Modifier,
    onPlayAgain  : () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Statistics, left-aligned and centered in the available space
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Summary Statistics",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(64.dp))
            Text("Round: $roundNum", fontSize = 24.sp)
            Spacer(Modifier.height(12.dp))

            // Handle uninitialized round at beginning
            if (answer.getLength() == 0) {
                Text("Sequence: None", fontSize = 24.sp)
            } else {
                Text("Sequence: ${answer.getIntList().joinToString()}", fontSize = 24.sp)
            }

            Spacer(Modifier.height(12.dp))
            // Horizontal Divider I found while searching documentation
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Gray
            )
            Spacer(Modifier.height(24.dp))

            // Handle uninitialized round at beginning
            val recentAttempts = if (summary.getRounds().isNotEmpty()) {
                val round = summary.getRounds().last()
                round.attempts
            } else {
                0
            }

            // Handle uninitialized round at beginning
            val recentTime: Duration = if (summary.getRounds().isNotEmpty()) {
                val round = summary.getRounds().last()
                round.time
            } else {
                Duration.ZERO
            }

            // ROUND STATISTICS
            Text("Round Attempts: $recentAttempts", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("Total Attempts: ${summary.getTotalAttempts()}", fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("Average Attempts: ${"%.2f".format(summary.getAverageAttempts())}", fontSize = 16.sp)

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Gray
            )
            Spacer(Modifier.height(24.dp))

            // TIME STATISTICS
            // Formatting from Citation [1]
            // Format time to floats of 2 decimal places, turn into doubles beforehand for compatibility
            Text("Round Time: ${"%.2f".format(recentTime.toDouble(DurationUnit.SECONDS))}s", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("Total Time: ${"%.2f".format(summary.getTotalTime().toDouble(DurationUnit.SECONDS))}s", fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("Average Time: ${"%.2f".format(summary.getMeanDuration().toDouble(DurationUnit.SECONDS))}s", fontSize = 16.sp)

            // CORRECT ATTEMPTS
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Gray
            )
            Spacer(Modifier.height(24.dp))

            // TIME STATISTICS
            Text("Accuracy: ${"%.0f".format(summary.getAccuracy())}%", fontSize = 24.sp)
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
                .height(64.dp)
                .width(216.dp)
                .fillMaxWidth(0.6f),
            onClick = {
                onPlayAgain()
            }
        ) {
            Text("Go Back", style = MaterialTheme.typography.titleLarge)
        }
    }
}