package dcuestab.gcuestab.com.components.timeline.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import dcuestab.gcuestab.com.components.timeline.R
import dcuestab.gcuestab.com.components.timeline.helper.measureView
import dcuestab.gcuestab.com.components.timeline.model.ScreenParams
import dcuestab.gcuestab.com.components.timeline.model.TemporalLinePoint

/**
 * Created by Guille on 14/09/2017.
 */

/**
 * This component is a ViewGroup that contains the time line and the selected area.
 *
 * The timeline is a view that is shown in a green and red color (green color indicates that the device is free and
 * red color indicates that the device is busy).
 *
 * The selected area is a view that indicates the time region that the user wants to book the device.
 */
class TimeLineGroup : FrameLayout {
    val MEASURE_NO_PAINT = 0
    val MEASURE_PAINT = 1
    var mPaintState = MEASURE_PAINT
        get
        set

    private val NOT_MEASURED : Int = 0
    private val MEASURED : Int = 1
    private var mMeasureState : Int = NOT_MEASURED

    /**
     * Width and height of this ViewGroup (dp).
     */
    private var mScreenParams : ScreenParams? = null

    /**
     * When we touch the selected area, we are touching in a (x, y) coordinates of the screen. The initial coordinates are "origX" and "origY".
     */
    private var mOrigX : Float = 0.0f
    private var mOrigY : Float = 0.0f

    private var mSelectedColor : Int = Color.YELLOW
    private var mMovementAllowed : Boolean = false

    private var mSelectedLine : SelectedTimeLine? = null
    private var mSelectedArea : TemporalLinePoint? = null

    constructor(context: Context) : super(context) {
        this.init(null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs == null || this.context == null)
            return

        val a = this.context.obtainStyledAttributes(attrs, R.styleable.TimeLineAttrs)
        if (a != null) {
            this.mSelectedColor = a.getColor(R.styleable.TimeLineAttrs_time_line_color_selected, Color.YELLOW)
            this.mMovementAllowed = a.getBoolean(R.styleable.TimeLineAttrs_time_line_movement_allowed, false)

            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.mScreenParams = measureView(widthMeasureSpec, heightMeasureSpec)

        if (this.mSelectedArea != null && this.mPaintState == MEASURE_PAINT)
        {
            this.mMeasureState = MEASURED
            this.removeSelectedTemporalLine()
            this.addSelectedTemporalLine(this.mSelectedArea!!)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun getSelectedInstance() {
        if (this.mSelectedLine == null)
            this.mSelectedLine = SelectedTimeLine(this.context)
    }

    /**
     * This method places a selected area over the temporal line.
     *
     * @param selectedArea The percentage star point and the percentage end point of the time line.
     */
    fun addSelectedTemporalLine(selectedArea : TemporalLinePoint) {
        if (this.mMeasureState == MEASURED) {
            if (this.mScreenParams != null) {
                // We calculate the width of the selected area (dp).
                val areaWidth: Int = ((selectedArea.temporalLineEnd - selectedArea.temporalLineStart) * this.mScreenParams!!.width).toInt()

                // We create and place the selected area view over the timeline in the position where it must be, and with the
                // width that it must have.
                this.getSelectedInstance()
                this.mSelectedLine?.mSelectedColor = this.mSelectedColor
                this.mSelectedLine?.x = this.mScreenParams!!.width * selectedArea.temporalLineStart
                this.addView(this.mSelectedLine, android.view.ViewGroup.LayoutParams(areaWidth, android.view.ViewGroup.LayoutParams.MATCH_PARENT))

                // With the touch event we move el selected area view.
                if (this.mMovementAllowed) {
                    this.mSelectedLine?.setOnTouchListener { view, motionEvent ->
                        this.touchLogic(motionEvent, areaWidth)
                        true
                    }
                }
            }
        }
        else
            this.mSelectedArea = selectedArea
    }

    private fun touchLogic(motionEvent : MotionEvent, areaWidth : Int)
    {
        when (motionEvent.action) {
        // We save the coordinates where we are touching the screen (x, y) just when we touch the selected area.
            MotionEvent.ACTION_DOWN -> {
                this.mOrigX = motionEvent.rawX
                this.mOrigY = motionEvent.rawY
            }

        // We move the selected area view.
            MotionEvent.ACTION_MOVE -> {
                // Distance that we have moved the selected area.
                val deltaX: Float = motionEvent.rawX - this.mOrigX
                val deltaY: Float = motionEvent.rawY - this.mOrigY

                // We calculate the new position of the selected area view.
                var posViewX : Float = 0.0f
                if (this.mSelectedLine != null) {
                    posViewX = this.mSelectedLine!!.x + deltaX
                    if (posViewX > this.mScreenParams!!.width - areaWidth)
                        posViewX = this.mScreenParams!!.width - areaWidth.toFloat()
                    else if (posViewX < 0)
                        posViewX = 0.0f

                    // We place the selected area view in the new position, and we repaint it ("Invalidate").
                    this.mSelectedLine!!.x = posViewX
                    this.mSelectedLine!!.invalidate()
                }

                // We calculate the new "origX" and "origY" values. They have to be changed to calculate a right value of
                // "deltaX" and "deltaY".
                this.mOrigX = motionEvent.rawX
                this.mOrigY = motionEvent.rawY
            }

        // When we finish touching the selected area, we invoke an event with the percentage start point and the percentage end point where it is palced.
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                // launch event
            }
        }
    }

    /**
     * This method removes the selected area over the temporal line.
     */
    fun removeSelectedTemporalLine()
    {
        if (this.hasSelectedArea())
        {
            this.removeView(this.mSelectedLine)
        }
    }

    /**
     * This method checks if the temporal line has a selected area.
     */
    fun hasSelectedArea() : Boolean
    {
        return this.mSelectedLine?.parent == this
    }
}