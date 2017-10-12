package dcuestab.gcuestab.com.components.loader.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import dcuestab.gcuestab.com.components.loader.R
import dcuestab.gcuestab.com.components.loader.listeners.AnimatedArcLoaderProgressListener

/**
 * Created by dacuesta on 17/9/17.
 *
 * This view animates ArcProgressView
 *
 * @param finalCurrent Final current progress
 * @param millis Current milliseconds to check the refresh of the view
 *
 * @param progressPerSecond Progress per second in percentage
 * @param progress Real progress per second
 *
 * @param progressListener Listener that sends the current progress until the end of the animation
 */
class AnimatedArcLoader : ArcLoader {
    var finalCurrent = 0f
    var millis : Long = 0
    var progressPerSecond = 1f
    var progress = 1f

    var progressListener : AnimatedArcLoaderProgressListener? = null




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

        if (current < finalCurrent) {
            val diff = System.currentTimeMillis() - millis
            current = Math.min(current + progress * diff / 1000, finalCurrent)

            millis = System.currentTimeMillis()

//            progressListener?.onProgress(current)

            invalidate()
        }
    }




    override fun changeProgress(current : Float, total : Float) {
        this.finalCurrent = current
        this.current = 0f
        this.total = total

        millis = System.currentTimeMillis()

        progress = progressPerSecond * total / 100

        invalidate()
    }

    fun changeProgress(current : Float, total : Float = this.total, progressPerSecond : Float) {
        this.progressPerSecond = progressPerSecond
        changeProgress(current, total)
    }




    private fun init(context : Context?, attrs: AttributeSet?) {
        finalCurrent = current
        current = 0f

        millis = System.currentTimeMillis()

        val a : TypedArray? = context?.getTheme()?.obtainStyledAttributes(
                attrs,
                R.styleable.AnimatedArcLoader,
                0, 0)

        try {
            progressPerSecond = a?.getFloat(R.styleable.AnimatedArcLoader_progressPerSecond, 1f) ?: 1f
        } finally {
            a?.recycle()
        }

        progress = progressPerSecond * total / 100
    }
}