package com.batikfy.batikfy.model.puzzle

data class Node(
    val puzzleStatePair: StatePair,
    val parent: Node?,
    var g: Int,
    private var h: Int
) {

    /**
     * Computes the f-value of this node (in relation to the A* algorithm).
     *
     * The f-value is the sum of the g- and h-values.
     */
    fun getF(): Int {
        return g + h
    }

    override fun equals(other: Any?): Boolean {
        return this.puzzleStatePair.puzzleState == (other as Node).puzzleStatePair.puzzleState
    }

    override fun hashCode(): Int {
        return hashState(puzzleStatePair.puzzleState)
    }

    companion object {
        fun hashState(puzzleState: ArrayList<Int>): Int {
            var hash = 0
            for (tile in puzzleState) {
                hash = hash * 10 + tile
            }

            return hash
        }
    }
}