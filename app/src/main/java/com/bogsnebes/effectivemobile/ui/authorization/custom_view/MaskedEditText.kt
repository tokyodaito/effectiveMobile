package com.bogsnebes.effectivemobile.ui.authorization.custom_view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.redmadrobot.inputmask.MaskedTextChangedListener

class MaskedEditText(context: Context, attrs: AttributeSet) : ClearableEditText(context, attrs) {

    private var listener: MaskedTextChangedListener? = null

    init {
        // Установка слушателя изменения текста с маской
        listener = MaskedTextChangedListener.installOn(
            this,
            "+7 [000] [000]-[00]-[00]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    toggleClearIcon(text?.isNotEmpty() == true)
                }
            }
        )

        // Установка слушателя фокуса, чтобы отображать маску
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && text.isNullOrEmpty()) {
                setText("+7 ")
                text?.let { setSelection(it.length) }
            }
        }

        // Фильтр для игнорирования первой введённой цифры '7'
        filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            if (dstart == 0 && source.startsWith("7")) {
                // Если первый символ - '7', игнорируем его
                " "
            } else {
                null // Продолжаем ввод как обычно
            }
        })
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        // Переключение иконки крестика, если текст изменён
        toggleClearIcon(text?.isNotEmpty() == true)
    }
}
