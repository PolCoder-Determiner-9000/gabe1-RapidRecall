package com.example.code

/* GameState Class
* Capture the current state of each round, to be cached later
* Parameters:
*   -roundNum: Int
*   -roundLength: Int
*   -sequence: Sequence
*/
class GameState {

    private var roundNum = 0
    private var roundLength = 0
    private var sequence: Sequence? = null

    /*
    * Increase round function
    * Increase round by one
    * Parameters: None
    * Returns: Unit
    */
    fun increaseRound() { roundNum += 1 }

    /*
    * InitializeSquence Function
    * Turn null sequence into a randomly-generated sequence
    * Parameters: input: Int (Length)
    * Returns: Unit
    */
    fun initializeSequence(input: Int) {
        roundLength = input
        sequence = Sequence(roundLength)
        sequence?.generate()
    }

    /*
    * ClearSequence
    * Clear sequence and length to allow regenerating values
    * Parameters: input: Unit
    * Returns: Unit
    */
    fun clearSequence() {
        roundLength = 0
        sequence = null
    }

    /*
    * Getter Functions
    * Allow other classes to get data, but encapsulate it by not setting it
    * Extra care is consider for the sequence, we consider a null sequence to be
    * of length 0 (For screens to show null values)
    */
    fun getRound(): Int = roundNum
    fun getLength(): Int = roundLength

    fun gameGetSequence(): Sequence {
        if (sequence == null ) {
            return Sequence(0)
        }
        return sequence!!
    }

}