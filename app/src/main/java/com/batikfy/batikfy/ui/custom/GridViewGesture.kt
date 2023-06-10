package com.batikfy.batikfy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridView
import com.batikfy.batikfy.model.puzzle.FlingDirection
import com.batikfy.batikfy.utils.puzzle.FlingDetector
import com.batikfy.batikfy.utils.puzzle.OnFlingListener
import kotlin.math.abs
import kotlin.math.roundToInt

class GridViewGesture: GridView {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var flingListener: OnFlingListener
    private var isFlinging: Boolean = false
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var touchSlopThreshold: Int = 0

    /**
     * Constructor Custom View
     */
    constructor(context: Context) : super(context) {
        detectGesture(context)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        detectGesture(context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        detectGesture(context)
    }

    /**
     * Sets the listener for responding to detected fling gestures.
     */
    fun setFlingListener(flingListener: OnFlingListener) {
        this.flingListener = flingListener
    }

    /**
     * Sets the distance in pixels a touch can wander before it is registered as a fling gesture.
     */
    fun setTouchSlopThreshold(touchSlopThreshold: Int) {
        this.touchSlopThreshold = touchSlopThreshold
    }

    /**
     * Sets the detector for the gestures and events.
     */
    private fun detectGesture(context: Context) {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(event: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val position: Int = pointToPosition(e1.x.roundToInt(), e1.y.roundToInt())
                    val direction: FlingDirection = FlingDetector.getDirection(e1, e2)
                    flingListener.onFling(direction, position)

                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })
    }

    /**
     * Implement this method to handle touch screen motion events.
     */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.actionMasked == MotionEvent.ACTION_UP) {
            performClick()
        }

        return gestureDetector.onTouchEvent(ev)
    }

    /**
     * Call this view's <code>OnClickListener</code>, if it is defined.
     * Performs all normal actions associated with clicking: reporting accessibility event,
     * playing a sound, etc.
     */
    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    /**
     * Implement this method to intercept all touch screen motion events. This allows you to watch
     * events as they are dispatched to your children, and take ownership of the current gesture at
     * any point.
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            gestureDetector.onTouchEvent(ev)
        }

        when (ev!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                isFlinging = false
            }

            MotionEvent.ACTION_MOVE -> {
                if (isFlinging) {
                    return true
                }

                /* Check if the difference between the coordinates is sufficient to be considered a fling. */
                val deltaX: Float = abs(ev.x - downX)
                val deltaY: Float = abs(ev.y - downY)

                if (deltaX > touchSlopThreshold || deltaY > touchSlopThreshold) {
                    isFlinging = true
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }
}