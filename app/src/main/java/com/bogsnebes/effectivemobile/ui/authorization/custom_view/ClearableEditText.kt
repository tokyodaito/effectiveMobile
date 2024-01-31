package com.bogsnebes.effectivemobile.ui.authorization.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bogsnebes.effectivemobile.R
import com.google.android.material.textfield.TextInputEditText

open class ClearableEditText(context: Context, attrs: AttributeSet) :
    TextInputEditText(context, attrs) {

    private var clearIcon: Drawable? = null

    init {
        clearIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_clear) // Замените на вашу иконку
        clearIcon?.let {
            DrawableCompat.setTint(it, currentHintTextColor)
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                toggleClearIcon(s?.isNotEmpty() == true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        setOnTouchListener { _, event ->
            clearIcon?.let {
                if (event.x > width - paddingRight - it.intrinsicWidth) {
                    this.text = null
                    true
                } else false
            } ?: false
        }

        toggleClearIcon(false)
    }

    protected fun toggleClearIcon(show: Boolean) {
        clearIcon = if (show) ContextCompat.getDrawable(context, R.drawable.ic_clear) else null
        setCompoundDrawablesWithIntrinsicBounds(null, null, clearIcon, null)
    }
}
