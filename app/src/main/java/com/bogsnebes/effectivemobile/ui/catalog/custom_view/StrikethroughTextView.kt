package com.bogsnebes.effectivemobile.ui.catalog.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class StrikethroughTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val linePaint = Paint().apply {
        color = currentTextColor // Или любой другой цвет
        strokeWidth = resources.displayMetrics.density * 2 // Установите толщину линии
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val startX = 0f
        val endX = width.toFloat()
        val lineY = height / 2f + (paint.descent() + paint.ascent()) / 2
        canvas?.drawLine(startX, lineY, endX, lineY, linePaint)
    }
}
