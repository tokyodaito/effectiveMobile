package com.bogsnebes.effectivemobile.ui.authorization.custom_view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.bogsnebes.effectivemobile.R

class ActiveButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    var isActive: Boolean = true
        set(value) {
            field = value
            updateButton()
        }

    private val activeColor = Color.parseColor("#D62F89")
    private val inactiveColor = Color.parseColor("#FF8AC9")
    private val radius = resources.getDimension(R.dimen.radius_8dp)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ActiveButton,
            0, 0
        ).apply {

            try {
                isActive = getBoolean(R.styleable.ActiveButton_active, true)
            } finally {
                recycle()
            }
        }
        updateButton()
    }

    private fun updateButton() {
        val drawable = GradientDrawable().apply {
            setColor(if (isActive) activeColor else inactiveColor)
            cornerRadius = radius
        }
        background = drawable
        isClickable = isActive
        text = "Войти"
        setTextColor(Color.WHITE) // Вы можете изменить цвет текста, если это необходимо
        // Установка шрифта, если он есть в ресурсах, например:
        // typeface = ResourcesCompat.getFont(context, R.font.your_font)
        textSize = 14f // Установите размер текста, который вы хотите
    }
}


