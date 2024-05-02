package com.example.custom_plus_2_compose_views.timepicker

import android.widget.NumberPicker

/**
 * OnScrollListener не дает точного значения, OnValueChangeListener триггерит каждое значение
 * Класс позволяет получать точное значение в SCROLL_STATE_IDLE
 */
internal abstract class CustomPickerChangeListener :
    NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {
    private var isScrolling = false

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        if (isScrolling) return
        onValueChanged(newVal)
    }

    override fun onScrollStateChange(view: NumberPicker, scrollState: Int) {
        isScrolling = scrollState != NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
        if (isScrolling) return
        onValueChanged(view.value)
    }

    abstract fun onValueChanged(value: Int)
}