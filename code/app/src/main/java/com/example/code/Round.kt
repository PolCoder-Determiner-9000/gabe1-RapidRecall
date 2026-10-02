package com.example.code

import kotlin.collections.mutableListOf
import kotlin.time.Duration
import kotlin.time.TimeSource

data class Round(
    val roundNumber: Int,
    val roundSequence: Sequence,
    val attempts: Int,
    val time: Duration,
    val length: Int
)