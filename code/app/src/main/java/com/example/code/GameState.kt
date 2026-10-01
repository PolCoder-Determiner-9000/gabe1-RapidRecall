package com.example.code

class GameState {

    private var roundNum = 1
    private var feedback = Summary()
    private var rounds = mutableListOf<Round>()
    var lengthInput = 0

    fun increaseRound() { roundNum += 1 }
    fun getRound(): Int = roundNum

    fun beginRound() {
        val inputSequence = Sequence(lengthInput)
        inputSequence.generate()
        val initRound = Round(inputSequence, lengthInput)
        rounds.add(initRound)
    }

    fun displaySequence() {
        val roundIndex = roundNum - 1
        val currentRound = rounds[roundIndex]
    }

}