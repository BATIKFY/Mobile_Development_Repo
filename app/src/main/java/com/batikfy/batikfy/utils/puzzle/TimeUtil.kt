package com.batikfy.batikfy.utils.puzzle

import java.util.*

class TimeUtil {
    companion object {
        private const val HOURS_TO_SECONDS = 3600
        private const val MINUTES_TO_SECONDS = 60
        const val SECONDS_TO_MILLISECONDS = 1000

        /**
         * Displays the specified number of seconds in the format h:mm:ss
         */
        fun displayTime(seconds: Long): String {
            return String.format(
                Locale.getDefault(),
                "%d:%02d:%02d",
                seconds / HOURS_TO_SECONDS,
                (seconds % HOURS_TO_SECONDS) / MINUTES_TO_SECONDS,
                seconds % MINUTES_TO_SECONDS
            )
        }
    }
}