package com.batikfy.batikfy.utils.puzzle

import com.batikfy.batikfy.model.puzzle.Node

class NodeByF : Comparator<Node> {
    /**
     * Compares its two arguments for order. Returns zero if the arguments are equal, a negative number
     * if the first argument is less than the second, or a positive number if the first argument is
     * greater than the second.
     */
    override fun compare(o1: Node?, o2: Node?): Int {
        if (o1?.getF()!! < o2?.getF()!!) {
            return -1
        } else if (o1.getF() > o2.getF()) {
            return 1
        }

        return 0
    }
}