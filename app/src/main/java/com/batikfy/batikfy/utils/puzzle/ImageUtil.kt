package com.batikfy.batikfy.utils.puzzle

import android.content.Context
import android.graphics.*

class ImageUtil {
    companion object {
        /**
         * Dark color filter for distinguishing the blank tile from the rest of the tiles.
         */
        private const val DARK_GRAY_FILTER = 0x121f1f

        /**
         * Converts the given drawable to a bitmap.
         */
        fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, drawableId)
        }

        /**
         * Resizes (crops) the given bitmap such that its dimensions are equal.
         */
        fun resizeToSquareBitmap(image: Bitmap, dstWidth: Int, dstHeight: Int): Bitmap {
            return Bitmap.createScaledBitmap(cropToSquareBitmap(image), dstWidth, dstHeight, true)
        }

        /**
         * Crops the given bitmap such that its dimensions are equal.
         *
         * The dimension of the resulting bitmap follows the smaller value between the original
         * width and height.
         */
        private fun cropToSquareBitmap(image: Bitmap): Bitmap {
            if (image.width >= image.height) {
                return Bitmap.createBitmap(
                    image,
                    image.width / 2 - image.height / 2,
                    0,
                    image.height,
                    image.height
                )
            } else {
                return Bitmap.createBitmap(
                    image,
                    0,
                    image.height / 2 - image.width / 2,
                    image.width,
                    image.width
                )
            }
        }

        /**
         * Splits the given bitmap into multiple square chunks.
         */
        fun splitBitmap(
            image: Bitmap,
            chunkDimen: Int,
            numTiles: Int,
            numColumns: Int
        ): Pair<ArrayList<Bitmap>, ArrayList<Bitmap>> {
            val chunks: ArrayList<Bitmap> = ArrayList(numTiles)
            val blankChunks: ArrayList<Bitmap> = ArrayList(numTiles)

            for (i in 0 until numTiles) {
                val chunk: Bitmap = Bitmap.createBitmap(
                    image,
                    (i % numColumns) * chunkDimen,
                    (i / numColumns) * chunkDimen,
                    chunkDimen,
                    chunkDimen
                )

                chunks.add(chunk)
                /* Darken the blank tile. */
                blankChunks.add(darkenBitmap(chunk))
            }

            return Pair(chunks, blankChunks)
        }

        /**
         * Applies a dark color filter on the given bitmap to designate it as the blank tile
         * and distinguish it from the rest of the puzzle tiles.
         */
        private fun darkenBitmap(image: Bitmap): Bitmap {
            val darkPicture: Bitmap = image.copy(image.config, true)
            val canvas = Canvas(darkPicture)

            val paint = Paint(Color.RED)
            paint.colorFilter = LightingColorFilter(DARK_GRAY_FILTER, 0x00000000)

            canvas.drawBitmap(darkPicture, Matrix(), paint)

            return darkPicture
        }
    }
}