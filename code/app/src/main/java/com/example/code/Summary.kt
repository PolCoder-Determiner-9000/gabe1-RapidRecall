package com.example.code

import kotlin.time.Duration

class Summary() {
    private var totalAttempts = 0
    private var averageAttempts = 0.0
    private var recentRound = 0

    private var totalTime = Duration.ZERO
    private var meanDuration = Duration.ZERO

    private var accuracy = 0.0

    private var rounds = mutableListOf<Round>()

    fun addRound(round: Round) { rounds.add(round) }

    fun calculateStatistics() {
        // Calculate totals
        totalAttempts = 0
        totalTime = Duration.ZERO
        for (round in rounds) {
            totalAttempts += round.attempts
            totalTime += round.time
        }
        if (rounds.isNotEmpty() && totalAttempts != 0) {
            recentRound = rounds.last().roundNumber
            averageAttempts = totalAttempts.toDouble() / rounds.size.toDouble()
            meanDuration = totalTime / rounds.size
            accuracy = (rounds.size.toDouble() / totalAttempts.toDouble()) * 100.0
        }
    }

    fun getTotalAttempts(): Int = totalAttempts
    fun getAverageAttempts(): Double = averageAttempts
    fun getRecentRound(): Int = recentRound
    fun getTotalTime(): Duration = totalTime
    fun getMeanDuration(): Duration = meanDuration
    fun getAccuracy(): Double = accuracy
    fun getRounds(): List<Round> = rounds.toList()

}