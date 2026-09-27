package com.example.code

import kotlin.collections.mutableListOf
import kotlin.time.Duration
import kotlin.time.TimeSource

class Round (
    val roundSequence: Sequence,
    val roundNumber: Int
) {
    private var isCorrect = false
    private var previousSequences = mutableListOf<Sequence>()
    var inputSequence: Sequence = Sequence(roundSequence.getLength())

    // Claude Time Checking https://claude.ai/chat/3ee37749-53fe-4b3e-9da2-cd811c45f2e0
    private val startTime = TimeSource.Monotonic.markNow()
    private var elapsed: Duration? = null
    private var ended: Boolean = false

    fun endGame() {
        if (ended) {
            return
        }

        if (!roundSequence.isEqual(inputSequence)) {
            return
        }

        elapsed = startTime.elapsedNow()
        ended = true
    }

    fun getElapsedTime(): Duration? = elapsed
    fun guessCount(): Int = previousSequences.count()
    fun getPreviousGuesses(): MutableList<Sequence> = previousSequences
}
