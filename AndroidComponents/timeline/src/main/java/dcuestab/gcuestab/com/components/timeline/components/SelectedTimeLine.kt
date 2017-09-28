package dcuestab.gcuestab.com.components.timeline.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dcuestab.gcuestab.com.components.timeline.helper.measureView
import dcuestab.gcuestab.com.components.timeline.model.ScreenParams

/**
 * Created by Guille on 14/09/2017.
 */
class SelectedTimeLine : View {
    private var mPaint : Paint? = null
    private var mScreenParams : ScreenParams? = null

    var mSelectedColor : Int = Color.YELLOW
        get
        set

    constructor(context: Context) : super(context) {
        this.init()

    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init()
    }

    private fun init() {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.mScreenParams = measureView(widthMeasureSpec, heightMeasureSpec)
        if (this.mScreenParams != null) {
            setMeasuredDimension(this.mScreenParams!!.width, this.mScreenParams!!.height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        this.mPaint = Paint()

        this.mPaint?.strokeWidth = 0.0f
        this.mPaint?.color = this.mSelectedColor

        if (this.mScreenParams != null) {
            canvas?.drawRect(0.0f, 0.0f, this.mScreenParams!!.width.toFloat(), this.mScreenParams!!.height.toFloat(), this.mPaint)
        }
    }
}