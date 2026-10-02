package com.example.code

import kotlin.time.Duration

/* Summary Class
* Cache everything related to the overall statistics
* Cache means, totals of time and round
* Parameters
*   -totalAttempts: Int
*   -averageAttempts: Int
*   -totalTime: Duration
*   -meanDuration: Duration
*   -accuracy: Double
*   -Rounds: mutableList<Round>
*/
class Summary() {
    private var totalAttempts = 0
    private var averageAttempts = 0.0
    private var recentRound = 0

    private var totalTime = Duration.ZERO
    private var meanDuration = Duration.ZERO

    private var accuracy = 0.0

    private var rounds = mutableListOf<Round>()

    /*
    * Add round function
    * Append recent round to overall rounds for statistics
    * Arguments: round: Round
    * Returns: Unit
    */
    fun addRound(round: Round) { rounds.add(round) }

    /*
    * Calculate Statistics Function
    * Calculate overall statistics, ensuring nothing gets divided by zero
    * Calculates mean/total time and attempts
    * to Calculate Accuracy (Round / total attempts)
    * Arguments: None
    * Returns: Unit
    */
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

    /*
    * Getter Functions
    * Allow some access to other classes, but maintain encapsulation
    * By not allowing them to edit them
    */
    fun getTotalAttempts(): Int = totalAttempts
    fun getAverageAttempts(): Double = averageAttempts
    fun getRecentRound(): Int = recentRound
    fun getTotalTime(): Duration = totalTime
    fun getMeanDuration(): Duration = meanDuration
    fun getAccuracy(): Double = accuracy
    fun getRounds(): List<Round> = rounds.toList()

}