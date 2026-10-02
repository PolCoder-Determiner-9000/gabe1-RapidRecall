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
        // See Citation [1]
        require(copyFrom.size == length) { "Expected $length elements, got ${copyFrom.size}" }
        sequence = copyFrom.toMutableList()
    }

    fun getIntList(): List<Int> = sequence.toList()
    fun getLength(): Int = length

}