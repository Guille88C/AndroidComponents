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
 * @param size Size of the line
 * @param cap Border of the line (butt, round, square)
 * @param color Color of the line
 * @param total Total progress
 * @param current Current progress
 */
open class ArcLoader : View {
    var size = 2f
    var cap = 0
    var color = Color.parseColor("#000000")
    var total = 100f
    var current = 0f




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

        val paint = Paint()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = size
        when (cap) {
            0 -> paint.strokeCap = Paint.Cap.BUTT
            1 -> paint.strokeCap = Paint.Cap.ROUND
            2 -> paint.strokeCap = Paint.Cap.SQUARE
        }

        val rect = RectF(size / 2f, size / 2f, width - size / 2f, height - size / 2f)

        val path = Path()
        path.addArc(rect, 0f, current * 360f / total)

        canvas?.drawPath(path, paint)
    }




    open fun changeProgress(current : Float, total : Float = this.total) {
        this.current = current
        this.total = total

        invalidate()
    }




    private fun init(context: Context?, attrs: AttributeSet?) {
        val a : TypedArray? = context?.getTheme()?.obtainStyledAttributes(
                attrs,
                R.styleable.ArcLoader,
                0, 0)

        try {
            size = (a?.getDimensionPixelSize(R.styleable.ArcLoader_size, 2) ?: 2).toFloat()
            cap = a?.getInt(R.styleable.ArcLoader_cap, 0) ?: 0
            color = a?.getInt(R.styleable.ArcLoader_color, Color.parseColor("#000000")) ?:  Color.parseColor("#000000")
            total = a?.getFloat(R.styleable.ArcLoader_total, 100f) ?: 100f
            current = a?.getFloat(R.styleable.ArcLoader_current, 0f) ?: 0f
        } finally {
            a?.recycle()
        }
    }
}