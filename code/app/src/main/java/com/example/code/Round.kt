package com.example.code

import kotlin.collections.mutableListOf
import kotlin.time.Duration
import kotlin.time.TimeSource

class Round (
    val roundSequence: Sequence,
    val roundNumber: Int
) {
    // Check for correctness of sequences
    private var isCorrect = false
    var inputSequence: Sequence = Sequence(roundSequence.getLength())
    private var previousSequences = mutableListOf<Sequence>()

    // Cache in Data for fun statistics
    private var accuracy = 0.0

    // Claude Time Checking https://claude.ai/chat/3ee37749-53fe-4b3e-9da2-cd811c45f2e0
    private val startTime = TimeSource.Monotonic.markNow()
    private var elapsed: Duration? = null

    fun handle_guess() {
        if (isCorrect || roundSequence.isEqual(inputSequence)) {
            isCorrect = true
            elapsed = startTime.elapsedNow()
        }

        previousSequences.add(inputSequence)
    }

    fun getElapsedTime(): Duration? = elapsed
    fun guessCount(): Int = if (isCorrect) { previousSequences.count() + 1 } else previousSequences.count()
    fun getPreviousGuesses(): MutableList<Sequence> = previousSequences

}
