package com.bogsnebes.effectivemobile.ui.information.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.bogsnebes.effectivemobile.R

class ExpandableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var textView: TextView
    private var button: TextView
    private var isExpanded = false
    private val maxLineCount = 3 // Максимальное количество строк в свернутом состоянии

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.expandable_text_view, this, true)

        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button.setTextColor(resources.getColor(R.color.category_item))

        // Начальная настройка видимости кнопки
        textView.post {
            button.isVisible = textView.lineCount > maxLineCount
        }

        button.setOnClickListener {
            toggle()
        }
    }

    fun setText(text: CharSequence) {
        textView.text = text

        // Проверяем, нужно ли отображать кнопку
        textView.post {
            button.isVisible = textView.lineCount > maxLineCount
        }
    }

    private fun toggle() {
        isExpanded = !isExpanded
        textView.maxLines = if (isExpanded) Integer.MAX_VALUE else maxLineCount
        button.text =
            if (isExpanded) context.getString(R.string.hide) else context.getString(R.string.show_more)
    }
}

