package com.example.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun DisplayNumbers(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    val temporaryList = listOf(6, 7, 5, 4, 3)

    var index by remember { mutableIntStateOf(0) }

    // Coroutine to iterate through stuff or something
    // Conversation: https://claude.ai/chat/bf00a379-e7c6-439a-8a68-95f51663889e
    LaunchedEffect(Unit) {
        for (i in temporaryList.indices) {
            index = i
            delay(1.seconds)
        }
        onFinished()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Number ${index + 1}/${temporaryList.size} of Sequence")
        Spacer(Modifier.height(8.dp))
        Text(temporaryList[index].toString(), fontSize = 64.sp)
    }
}

@Composable
fun GuessNumberScreen(
    modifier: Modifier = Modifier,
    onDone: () -> Unit
) {
    // TODO: TEMPORARY VARIABLES THAT SHOULD BE REMOVED
    var inputGuess by remember { mutableStateOf("") }
    val guesses = remember { mutableStateListOf("12345", "67890", "11111") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = inputGuess,
                onValueChange = { inputGuess = it.filter { c -> c.isDigit() } },
                label = { Text("Enter Your Guess...") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Button(
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = {
                    if (inputGuess == "100") {
                        onDone()
                    }
                }
            ) {
                // Start Display Numbers
                Text("OK")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Time & Attempts
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Elapsed Time")
                Spacer(modifier = Modifier.height(8.dp))
                Text("0:00")
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Attempts")
                Spacer(modifier = Modifier.height(8.dp))
                Text("0")
            }
        }

        // Previous Guesses
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Previous Guesses")
            GuessHistory(
                guesses = guesses,
                modifier = Modifier.weight(1f)   // takes the remaining space
            )

        }

    }

}

@Composable
fun GuessHistory(
    guesses: List<String>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    // Scroll to the newest guess whenever one is added
//    LaunchedEffect(guesses.size) {
//        if (guesses.isNotEmpty()) listState.animateScrollToItem(guesses.lastIndex)
//    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(guesses) { guess ->
            Text(guess)
        }
    }
}