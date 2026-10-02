package com.example.code

import kotlin.collections.mutableListOf
import kotlin.time.Duration
import kotlin.time.TimeSource

/* Round Data Class
* Cache the local round after completion, used for summary statistics calculations
* and to log each round.
*/
data class Round(
    val roundNumber: Int,
    val roundSequence: Sequence,
    val attempts: Int,
    val time: Duration,
    val length: Int,
)