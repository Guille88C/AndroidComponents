package dcuestab.gcuestab.com.components.timeline.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dcuestab.gcuestab.com.components.timeline.R
import dcuestab.gcuestab.com.components.timeline.helper.measureView
import dcuestab.gcuestab.com.components.timeline.model.ScreenParams
import dcuestab.gcuestab.com.components.timeline.model.TemporalLinePoint

/**
 * Created by Guille on 14/09/2017.
 */

/**
 * This component is used as the temporal line. Grenn and red colors are used here.
 *
 * Green color let us know when the device is available.
 *
 * Red color let us kenow when the device is busy.
 */
class TimeLine : View {
    private var mBusyColor : Int = Color.RED
    private var mAvailableColor : Int = Color.GREEN

    private var mPaint : Paint? = null
    private var mScreenParams : ScreenParams? = null

    /**
     * This property stores the busy point in the temporal line. They are painted in red.
     */
    var listBusyArea : List<TemporalLinePoint>? = null
        get
        set

    constructor(context: Context) : super(context) {
        this.init(null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(attrs)
    }

    private fun init (attrs: AttributeSet?) {
        if (attrs == null || this.context == null)
            return

        val a = this.context.obtainStyledAttributes(attrs, R.styleable.TimeLineAttrs)
        if (a != null) {
            this.mBusyColor = a.getColor(R.styleable.TimeLineAttrs_time_line_color_busy, Color.RED)
            this.mAvailableColor = a.getColor(R.styleable.TimeLineAttrs_time_line_color_available, Color.GREEN)

            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        this.mPaint = Paint()

        // Green color for all the component
        this.mPaint?.strokeWidth = 0.0f
        this.mPaint?.color = this.mAvailableColor
        if (this.mScreenParams != null) {
            canvas?.drawRect(0.0f, 0.0f, this.mScreenParams!!.width.toFloat(), this.mScreenParams!!.height.toFloat(), this.mPaint)
        }

        // Red color for the busy areas
        if (this.mScreenParams != null && this.listBusyArea != null && this.listBusyArea!!.size > 0) {
            this.listBusyArea?.forEach {
                item ->
                    // The "TemporalLinePoint" reperesents the percentages of the temporal line.
                    val percStart : Float = item.temporalLineStart
                    val percEnd : Float = item.temporalLineEnd
                    // We calculate the real points.
                    val pointStart : Float = (this.mScreenParams!!.width * percStart)
                    val pointEnd : Float = (this.mScreenParams!!.width * percEnd)
                    // We paint the rectangles.
                    this.mPaint?.color = this.mBusyColor
                    canvas?.drawRect(pointStart, 0.0f, pointEnd, this.mScreenParams!!.height.toFloat(), this.mPaint)

            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.mScreenParams = measureView(widthMeasureSpec, heightMeasureSpec)
        if (this.mScreenParams != null) {
            setMeasuredDimension(this.mScreenParams!!.width, this.mScreenParams!!.height)
        }
    }
}