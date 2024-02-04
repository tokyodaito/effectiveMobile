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
        // Начальные координаты (нижний левый угол)
        val startX = 0f
        val startY = height.toFloat() - 20
        // Конечные координаты (правый верхний угол)
        val endX = width.toFloat() + 130
        val endY = 0f

        // Рисование линии от нижнего левого до верхнего правого угла
        canvas?.drawLine(startX, startY, endX, endY, linePaint)
    }
}
