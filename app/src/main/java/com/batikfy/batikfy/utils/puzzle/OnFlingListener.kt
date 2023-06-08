package com.batikfy.batikfy.utils.puzzle

import com.batikfy.batikfy.model.puzzle.FlingDirection

interface OnFlingListener {
    /**
     * Defines the response to the detected fling gesture given the fling direction and the position
     * of the flung tile in the puzzle grid.
     */
    fun onFling(direction: FlingDirection, position: Int)
}