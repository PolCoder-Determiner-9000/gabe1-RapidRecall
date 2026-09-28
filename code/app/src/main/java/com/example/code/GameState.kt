package com.example.code

import kotlin.time.Duration
import kotlin.time.TimeSource

class GameState {
    private var screen = ScreenState.SETUP
    private var roundNum = 0
    private var feedback = Feedback()

    var roundLength: Int = 0

    fun increaseRound() { roundNum += 1 }



}