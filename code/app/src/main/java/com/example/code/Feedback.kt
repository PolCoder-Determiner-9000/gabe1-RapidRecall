package com.example.code

import kotlin.time.Duration

class Feedback(

) {
    var meanGuess: Double = 0.0
    var totalAttempts: UInt = 0U
    var successfulAttempts: UInt = 0U

    var guess_attempts = mutableListOf<Int>()
    var previous_guesses = mutableMapOf<Int, MutableList<Sequence>>()
    var time_attempts = mutableListOf<Duration>()

}