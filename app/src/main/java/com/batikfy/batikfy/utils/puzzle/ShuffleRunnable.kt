package com.batikfy.batikfy.utils.puzzle

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.batikfy.batikfy.model.puzzle.Key
import java.util.concurrent.atomic.AtomicInteger

class ShuffleRunnable(
    private val handler: Handler,
    private val tilePosition: Int,
    private val numTiles: Int
) : Runnable {

    override fun run() {
        val message = Message.obtain()

        val bundle = Bundle()
        bundle.putInt(Key.KEY_TILE_POSITION.name, tilePosition)
        bundle.putInt(Key.KEY_PROGRESS.name, progress.incrementAndGet())

        /* Subtract 1 to take blank tile into account. */
        progress.compareAndSet(numTiles - 1, 0)

        message.data = bundle
        handler.sendMessage(message)
    }

    companion object {
        /**
         * Progress of the shuffling (stored in an <code>AtomicInteger</code> to prevent thread-related issues).
         */
        private val progress = AtomicInteger(0)
    }
}