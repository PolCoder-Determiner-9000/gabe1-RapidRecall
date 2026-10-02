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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Duration
import kotlin.time.TimeSource

@Composable
fun DisplayNumbers(
    sequence  : List<Int>,
    roundNum  : Int,
    modifier  : Modifier = Modifier,
    onFinished: () -> Unit
) {
    var index by remember { mutableIntStateOf(0) }

    // Coroutine to iterate through stuff or something
    // See Citation [3]
    LaunchedEffect(Unit) {
        for (i in sequence.indices) {
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
        Spacer(Modifier.height(128.dp))
        Text(
            text = "Round $roundNum",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Number ${index + 1}/${sequence.size} of Sequence",
            fontSize = 24.sp,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = sequence[index].toString(),
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun GuessNumberScreen(
    answer: Sequence,
    roundNumber: Int,
    modifier: Modifier = Modifier,
    summary: Summary,
    onDone  : () -> Unit
) {
    var inputGuess by remember { mutableStateOf("") }
    val guesses = remember { mutableStateListOf<String>() }
    var feedbackMessage by remember { mutableStateOf("Guess A Number...") }
    var attemptNumber by remember { mutableIntStateOf(0) }

    // Elapsed Time
    // See Citation [1]
    val mark = remember { TimeSource.Monotonic.markNow() }
    var elapsed by remember { mutableStateOf(Duration.ZERO) }
    var finalTime by remember { mutableStateOf<Duration?>(null) }

    // Run Coroutines to
    // Coroutine to delay the game ending to signal that you're correct
    LaunchedEffect(finalTime) {
        finalTime?.let {
            delay(2.seconds)
            onDone()
        }
    }

    // Coroutine to mark time as the game goes on
    LaunchedEffect(Unit) {
        while (true) {
            elapsed = mark.elapsedNow()
            delay(1.seconds)
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(64.dp))
        Text(
            text = feedbackMessage,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))

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
                // See Citation [2]
                // Disable input when user is correct
                enabled = feedbackMessage != "You're correct!",
                onClick = {
                    val tempSequence = stringToSequence(inputGuess)
                    guesses.add(inputGuess)
                    attemptNumber += 1
                    if (isEqual(answer, tempSequence)) {
                        finalTime = mark.elapsedNow()
                        val roundStats = Round(
                            roundNumber = roundNumber,
                            roundSequence = answer,
                            attempts = attemptNumber,
                            time = finalTime!!,
                            length = answer.getLength()
                        )
                        summary.addRound(roundStats)
                        summary.calculateStatistics()
                        feedbackMessage = "You're correct!"
                    } else {
                        feedbackMessage = if (attemptNumber == 1 ) "You're Wrong!" else {
                            "You're wrong $attemptNumber times!"
                        }
                        inputGuess = ""
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
                Text(
                    text = "Elapsed Time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (feedbackMessage == "You're correct!" && finalTime != null) {
                    Text(
                        text = finalTime!!.toComponents { minutes, seconds, _ -> "%d:%02d".format(minutes, seconds) },
                        fontSize = 24.sp,
                    )
                } else {
                    Text(
                        text = elapsed.toComponents { minutes, seconds, _ -> "%d:%02d".format(minutes, seconds) },
                        fontSize = 24.sp
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Attempts",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = attemptNumber.toString(),
                    fontSize = 24.sp,
                )
            }
        }

        // Previous Guesses
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Previous Guesses",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            GuessHistory(
                guesses = guesses,
                modifier = Modifier.weight(1f), // takes the remaining space
                answer = answer
            )
        }
    }
}

@Composable
fun GuessHistory(
    guesses: List<String>,
    answer: Sequence,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(guesses) { guess ->
            val tempSequence = stringToSequence(guess)
            Text(
                text = coloredSequenceText(tempSequence, answer),
                fontSize = 30.sp,
            )
        }
    }
}

/*
* Wordle-Style Feedback System
* Through every sequence (as one sequence, a bit confusing but that's how I like to see it)
* We compare each digit (lesser/greater) than the answer
* Extraneous answers are gray.
* See Citation [1]
*/
fun coloredSequenceText(input: Sequence, answer: Sequence): AnnotatedString {
    val guess = input.getIntList()
    val target = answer.getIntList()
    val green = Color(0xFF00A800)
    val orange = Color(0xFFE88A16)
    val red = Color(0xFFE11B42)

    // Return a String that supports Colourful String Support for Wordle-Style feedback
    return buildAnnotatedString {
        var i = 0
        // While loop for guess string (Which may vary and not equal to the answer)
        while (i < guess.size) {
            // Colour it Green or the Default text colour if it's an exact match
            val isMatch = i < target.size && guess[i] == target[i]
            val isGreater = i < target.size && guess[i] > target[i]
            val isLesser = i < target.size && guess[i] < target[i]
            val color = if (isMatch) {
                green
            } else if (isGreater) {
                orange
            } else if (isLesser) {
                red
            } else {
                Color.Unspecified
            }

            // Fill the rest if the guess is larger than the answer
            withStyle(SpanStyle(color = color)) {
                append(guess[i].toString())
            }
            i++
        }
    }
}
fun isEqual(a: Sequence, b: Sequence): Boolean {
    if (a.getLength() != b.getLength()) return false
    return a.getIntList() == b.getIntList()
}

fun stringToSequence(input: String): Sequence {
    // Parse string of integers into ints
    // See Citation [1]
    val digits: List<Int> = input.map { it.digitToInt() }
    val length = digits.count()

    val result = Sequence(length)
    result.copy(digits)
    return result
}