package com.example.custom_plus_2_compose_views.timepicker

internal object TimePickerUtils {
    private const val TIME_PICKER_MASK: String = "%02d"
    internal fun formatTimePicker(time: Int) = String.format(TIME_PICKER_MASK, time)
}