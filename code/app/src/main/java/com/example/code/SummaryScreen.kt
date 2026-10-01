package com.example.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    onPlayAgain: () -> Unit
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
            Text("Summary Statistics", fontSize = 32.sp)
            Spacer(Modifier.height(8.dp))
            Text("Round:", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("Sequence:", fontSize = 24.sp)

            Spacer(Modifier.height(16.dp))

            // ROUND STATISTICS
            Text("Round Attempts:", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("Total Attempts:", fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("Average Attempts:", fontSize = 16.sp)

            Spacer(Modifier.height(16.dp))

            // TIME STATISTICS
            Text("Round Time:", fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text("Total Time:", fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("Average Time:", fontSize = 16.sp)
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
                .height(56.dp)
                .fillMaxWidth(0.6f),
            onClick = {
                onPlayAgain()
            }
        ) {
            Text("Play Again")
        }
    }
}