package com.example.code

/*
* Sequence Class
* Handles caching in and
* Parameters: Length (Int), Sequence: MutableList<Int> (List of numbers)
* Methods:
*   - Generate(): Unit - (Generates a random sequence),
*   - Copy(copyFrom: List<Int>): Unit - (Copies a sequence from a list of equal length)
*   - getIntList(): List<Int> - Get read-only list of numbers
*/
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