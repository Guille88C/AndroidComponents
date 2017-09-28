package dcuestab.gcuestab.com.components.timeline.helper

import android.view.View
import dcuestab.gcuestab.com.components.timeline.model.ScreenParams

/**
 * Created by Guille on 14/09/2017.
 */

/**
 *  With this method we calculate the width and the height of a view.
 *
 *  Note: We must use this method in the OnMeasure method of a View.
 *
 *  @param widthMeasureSpec OnMeasure param.
 *  @param heightMeasureSpec OnMeasure param.
 *
 *  @return Width and height of the view.
 */
fun measureView(widthMeasureSpec : Int, heightMeasureSpec : Int) : ScreenParams {
    val desiredWidth : Int = 100
    val desiredHeight : Int = 100

    var sParams : ScreenParams = ScreenParams()

    val widthMode : Int = View.MeasureSpec.getMode(widthMeasureSpec)
    val widthSize : Int = View.MeasureSpec.getSize(widthMeasureSpec)
    val heightMode : Int = View.MeasureSpec.getMode(heightMeasureSpec)
    val heightSize : Int = View.MeasureSpec.getSize(heightMeasureSpec)

    if (widthMode == View.MeasureSpec.EXACTLY) {
        sParams.width = widthSize
    }
    else if (widthMode == View.MeasureSpec.AT_MOST) {
        sParams.width = Math.min(desiredWidth, widthSize)
    }
    else {
        sParams.width = desiredWidth
    }

    if (heightMode == View.MeasureSpec.EXACTLY) {
        sParams.height = heightSize
    }
    else if (heightMode == View.MeasureSpec.AT_MOST) {
        sParams.height = Math.min(desiredHeight, heightSize)
    }
    else {
        sParams.height = desiredHeight
    }

    return sParams
}