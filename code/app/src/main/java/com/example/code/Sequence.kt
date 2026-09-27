package com.example.code

class Sequence(
    private val length: Int
) {
    private var sequence = MutableList(length) { 0 }
    private var generated: Boolean = false

    fun generateSequence() {
        if (generated) {
            return
        }

        for (i in 0..<length) {
            val randomNum = (0..9).random()
            sequence[i] = randomNum
        }

        generated = true
    }

    fun isEqual(comparison: Sequence): Boolean {
        val otherSequence = comparison.getSequence()
        val otherLength = comparison.getLength()

        if (otherLength != length) {
            return false
        }

        for (i in 0..<length) {
            if (otherSequence[i] != sequence[i]) {
                return false
            }
        }

        return true
    }

    fun getSequence(): MutableList<Int> = sequence
    fun getLength(): Int = length

}