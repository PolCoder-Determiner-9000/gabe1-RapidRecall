package com.example.code

class Sequence(
    private val length: Int
) {
    private var sequence = MutableList<Int>(length) { 0 }

    fun generate() {
        for (i in 0..<length) {
            val randomNum = (0..9).random()
            sequence[i] = randomNum
        }
    }

    fun copy(copyFrom: List<Int>) {
        // https://claude.ai/chat/097e3c5a-967c-4ade-8335-a27dee356a91
        require(copyFrom.size == length) { "Expected $length elements, got ${copyFrom.size}" }
        sequence = copyFrom.toMutableList()
    }

    fun getIntList(): List<Int> = sequence.toList()
    fun getLength(): Int = length

}