package com.example.code

class Sequence(
    private val length: Int
) {
    private var sequence = MutableList<Int>(length) { 0 }
    private var generated: Boolean = false

    fun generate() {
        if (generated) {
            return
        }

        for (i in 0..<length) {
            val randomNum = (0..9).random()
            sequence[i] = randomNum
        }

        generated = true
    }

    fun setSequence(input: MutableList<Int>) {
        if (input.size != length) {
            throw IllegalArgumentException("Input size must equal Number")
        }

        sequence = input.toMutableList()
    }

    fun isEqual(comparison: Sequence): Boolean {

        val otherSequence = comparison.getSequence()
        val otherLength = comparison.getLength()

        if (otherLength != length) {
            return false
        }

        // Compare sequence values
        return otherSequence == sequence
    }

    fun getSequence(): List<Int> = sequence.toList()
    fun getLength(): Int = length

}