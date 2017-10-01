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
 * @param refresh Milliseconds to refresh the view
 * @param event Event
 */
class AnimatedArcLoader : ArcLoader {
    private var finalCurrent : Int = 0
    private var millis : Long = 0
    private var increase : Int = 1

    var refresh : Int = 10
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
            val diff : Long = System.currentTimeMillis() - millis
            if (diff > refresh) {
                current = Math.min((current + increase * diff / refresh).toInt(), finalCurrent)
                millis = System.currentTimeMillis()
                progressListener?.onProgress(current)
            }
            invalidate()
        }
    }


    private fun init(context : Context?, attrs: AttributeSet?) {
        finalCurrent = current
        current = 0

        millis = System.currentTimeMillis()

        increase = Math.max((total * .01f).toInt(), 1)

        val a : TypedArray? = context?.getTheme()?.obtainStyledAttributes(
                attrs,
                R.styleable.AnimatedArcLoader,
                0, 0)

        try {
            refresh = a?.getInteger(R.styleable.AnimatedArcLoader_refresh, 10) ?: 10
        } finally {
            a?.recycle()
        }
    }
}