package dcuestab.gcuestab.com.components.loader.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import dcuestab.gcuestab.com.components.loader.R


/**
 * Created by dacuesta on 17/9/17.
 *
 * This view creates a circle progress bar. It has an outside line (complete circle) and has an
 * inside line (current progress).
 *
 * @param outsideLineSize Size of the outside line
 * @param outsideLineCap Border of the outside line (butt, round, square)
 * @param outsideLineColor Color of the outside line
 *
 * @param insideLineSize Size of the inside line
 * @param insideLineCap Border of the inside line (butt, round, square)
 * @param insideLineColor Color of the inside line
 *
 * @param total Total progress
 * @param current Current progress
 */
open class ArcLoader : View {
    var outsideLineSize : Int = 4
    var insideLineSize : Int = 2

    var outsideLineCap : Int = 0
    var insideLineCap : Int = 0

    var outsideLineColor : Int = Color.parseColor("#000000")
    var insideLineColor : Int = Color.parseColor("#FF0000")

    var total : Int = 360
    var current : Int = 0


    constructor(context: Context?) : super(context) {
        init(context, null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val initRect : Float = Math.max(outsideLineSize, insideLineSize).toFloat() / 2f

        drawArc(initRect, measuredWidth.toFloat(), measuredWidth.toFloat(), 0f, 360f, outsideLineSize.toFloat(),
                outsideLineColor, outsideLineCap, canvas)

        val sweepAngle : Float = current * 360f / total
        drawArc(initRect, measuredWidth.toFloat(), measuredWidth.toFloat(), -180f, sweepAngle, insideLineSize.toFloat(),
                insideLineColor, insideLineCap, canvas)
    }


    private fun init(context: Context?, attrs: AttributeSet?) {
        val a : TypedArray? = context?.getTheme()?.obtainStyledAttributes(
                attrs,
                R.styleable.ArcLoader,
                0, 0)

        try {
            outsideLineSize = a?.getDimensionPixelSize(R.styleable.ArcLoader_outsideLineSize, 4) ?: 4
            insideLineSize = a?.getDimensionPixelSize(R.styleable.ArcLoader_insideLineSize, 2) ?: 2

            outsideLineCap = a?.getInt(R.styleable.ArcLoader_outsideLineCap, 0) ?: 0
            insideLineCap = a?.getInt(R.styleable.ArcLoader_insideLineCap, 0) ?: 0

            outsideLineColor = a?.getInt(R.styleable.ArcLoader_outsideLineColor, Color.parseColor("#000000")) ?:  Color.parseColor("#000000")
            insideLineColor = a?.getInt(R.styleable.ArcLoader_insideLineColor, Color.parseColor("#FF0000")) ?: Color.parseColor("#FF0000")

            total = a?.getInt(R.styleable.ArcLoader_total, 360) ?: 360
            current = a?.getInt(R.styleable.ArcLoader_current, 0) ?: 0
        } finally {
            a?.recycle()
        }
    }

    private fun drawArc(initRect : Float, width : Float, height : Float, startAngle : Float, sweepAngle : Float, lineSize : Float, color : Int,
                        lineCap : Int, canvas : Canvas?) {
        val paint : Paint = Paint()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineSize
        when (lineCap) {
            0 -> paint.strokeCap = Paint.Cap.BUTT
            1 -> paint.strokeCap = Paint.Cap.ROUND
            2 -> paint.strokeCap = Paint.Cap.SQUARE
        }

        val rect : RectF = RectF(initRect, initRect, width - initRect, height - initRect)

        val path : Path = Path()
        path.addArc(rect, startAngle, sweepAngle)

        canvas?.drawPath(path, paint)
    }
}