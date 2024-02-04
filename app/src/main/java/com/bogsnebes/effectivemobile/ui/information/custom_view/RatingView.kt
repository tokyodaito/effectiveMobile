package com.bogsnebes.effectivemobile.ui.information.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.bogsnebes.effectivemobile.R

class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var estimation = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var starFull: Drawable? = null
    private var starHalf: Drawable? = null
    private var starEmpty: Drawable? = null

    init {
        starFull = ContextCompat.getDrawable(context, R.drawable.ic_star_full)
        starHalf = ContextCompat.getDrawable(context, R.drawable.ic_star_half)
        starEmpty = ContextCompat.getDrawable(context, R.drawable.ic_star_empty)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val starWidth: Int = starFull?.intrinsicWidth ?: 0
        val starHeight: Int = starFull?.intrinsicHeight ?: 0
        val starMargin: Int = 5 // отступ между звездами

        var left = 0
        for (i in 1..5) {
            when {
                i <= estimation -> starFull?.setBounds(left, 0, left + starWidth, starHeight)
                i - estimation < 1 -> starHalf?.setBounds(left, 0, left + starWidth, starHeight)
                else -> starEmpty?.setBounds(left, 0, left + starWidth, starHeight)
            }
            left += starWidth + starMargin

            starFull?.draw(canvas)
            starHalf?.draw(canvas)
            starEmpty?.draw(canvas)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val starWidth: Int = starFull?.intrinsicWidth ?: 0
        val starHeight: Int = starFull?.intrinsicHeight ?: 0
        val width = (starWidth * 5) + (4 * 10) // 4 отступа между звездами
        setMeasuredDimension(width, starHeight)
    }
}
