package com.batikfy.batikfy.utils.puzzle

class AnimationUtil {
    companion object {
        /**
         * Upper bound of the duration of the animation of the progress bar during shuffling.
         */
        const val SHUFFLING_ANIMATION_UPPER_BOUND = 2700

        /**
         * Offset of the duration of the animation of the progress bar during shuffling.
         */
        const val SHUFFLING_ANIMATION_OFFSET = 300

        /**
         * Maximum duration of the display of the success message once a game is completed
         * (that is, the shuffled puzzle is solved).
         */
        const val SUCCESS_DISPLAY = 5000

        /**
         * Delay before the first move in the animation of the puzzle solution is displayed.
         */
        const val FIRST_MOVE_SOLUTION_DELAY = 750

        /**
         * Delay between the sliding of tiles during the animation of the puzzle solution.
         */
        const val MOVE_SOLUTION_DELAY = 425
    }
}