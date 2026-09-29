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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
                    SetupScreen(
                        roundNumber = gameState.getRound(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeTheme {
        Greeting("Android")
    }
}

@Composable
fun SetupScreen(
    roundNumber: Int,
    modifier: Modifier = Modifier
) {
    var lengthInput by remember { mutableStateOf("") }
    var lengthNum: Int? by remember { mutableStateOf(null) }
    var lengthFeedback by remember { mutableStateOf("Current Length: ${lengthNum ?: 0}")}

    Column(
        modifier = Modifier.fillMaxSize(),
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

        Row(
            modifier = Modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = lengthInput,
                onValueChange = { lengthInput = it.filter { c -> c.isDigit() } },
                label = { Text("Length") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Button(
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = {
                    lengthNum = lengthInput.toIntOrNull()
                    lengthFeedback = if (lengthNum != null && lengthNum in 1..10) {
                        "Current Length: $lengthNum"
                    } else {
                        "Please enter a number from 1 to 10"
                    }
                }
            ) {
                Text("Enter Length")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(lengthFeedback)

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.padding(vertical = 12.dp),
            onClick = {

            }
        ) {
            Text("Begin Round")
        }
    }
}