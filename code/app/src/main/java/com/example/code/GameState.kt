package com.example.code

class GameState {

    private var roundNum = 0
    private var roundLength = 0
    private var sequence: Sequence? = null

    fun increaseRound() { roundNum += 1 }

    fun initializeSequence(input: Int) {
        roundLength = input
        sequence = Sequence(roundLength)
        sequence?.generate()
    }

    fun clearSequence() {
        roundLength = 0
        sequence = null
    }
    fun getRound(): Int = roundNum
    fun getLength(): Int = roundLength

    fun gameGetSequence(): Sequence {
        if (sequence == null ) {
            return Sequence(0)
        }
        return sequence!!
    }


}