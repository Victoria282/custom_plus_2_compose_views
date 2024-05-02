package com.example.custom_plus_2_compose_views.timepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.intersvyaz.extensions.setGone
import com.intersvyaz.extensions.setVisible
import net.intersvyaz.library.core_common_ui.R
import com.example.custom_plus_2_compose_views.timepicker.TimePickerUtils.formatTimePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

public class CustomTimePicker @JvmOverloads constructor(
    private val ctx: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(ctx, attrs) {

    private var _view: View? = null
    private val view get() = checkNotNull(_view)

    private var _hoursPicker: NumberPicker? = null
    private val hoursPicker get() = checkNotNull(_hoursPicker)

    private var _minutesPicker: NumberPicker? = null
    private val minutesPicker get() = checkNotNull(_minutesPicker)

    private var _secondsPicker: NumberPicker? = null
    private val secondsPicker get() = checkNotNull(_secondsPicker)

    private val maxValueHours: Int = MAX_VALUE_HOURS
    private val maxValueMinutes: Int = MAX_VALUE_MIN_SEC
    private val maxValueSeconds: Int = MAX_VALUE_MIN_SEC

    private val minValueTime: Int = 0
    private var pickerType: CustomTimePickerType = CustomTimePickerType.SECONDS

    private var timepickerCallback: TimepickerCallback? = null

    private val hoursListener = object : CustomPickerChangeListener() {
        override fun onValueChanged(value: Int) {
            timeSetOption(value, minutesPicker.value, secondsPicker.value, true)
        }
    }

    private val minutesListener = object : CustomPickerChangeListener() {
        override fun onValueChanged(value: Int) {
            timeSetOption(hoursPicker.value, value, secondsPicker.value, true)
        }
    }

    private val secondsListener = object : CustomPickerChangeListener() {
        override fun onValueChanged(value: Int) {
            timeSetOption(hoursPicker.value, minutesPicker.value, value, true)
        }
    }

    public var timeSetOption: (
        h: Int,
        m: Int,
        s: Int,
        withCallback: Boolean
    ) -> Unit = { hours, minutes, seconds, withCallback ->
        hoursPicker.value = hours
        minutesPicker.value = minutes
        secondsPicker.value = seconds

        if (withCallback) timepickerCallback?.onTimeChanged(hours, minutes, seconds)
    }

    init {
        getAttributes(attrs)
        initViews()
        setupTimepicker()
        initTimepickerListeners()
    }

    private fun getAttributes(attrs: AttributeSet?) {
        val typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTimePicker, 0, 0)
        pickerType = when (typedArray.getInt(R.styleable.CustomTimePicker_pickerType, 0)) {
            0 -> CustomTimePickerType.SECONDS
            1 -> CustomTimePickerType.MINUTES
            else -> CustomTimePickerType.HOURS
        }

        typedArray.recycle()
    }

    private fun initViews() {
        val inflate = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _view = inflate.inflate(R.layout.time_picker_layout, this, true)
        _hoursPicker = findViewById(R.id.hours)
        _minutesPicker = findViewById(R.id.minutes)
        _secondsPicker = findViewById(R.id.seconds)

        when (pickerType) {
            CustomTimePickerType.HOURS -> {
                hoursPicker.setFormatter { formatTimePicker(it) }
                hoursPicker.setVisible()
                minutesPicker.setGone()
                secondsPicker.setGone()
            }

            CustomTimePickerType.MINUTES -> {
                hoursPicker.setFormatter { formatTimePicker(it) }
                minutesPicker.setFormatter { formatTimePicker(it) }
                hoursPicker.setVisible()
                minutesPicker.setVisible()
                secondsPicker.setGone()
            }

            CustomTimePickerType.SECONDS -> {
                hoursPicker.setFormatter { formatTimePicker(it) }
                minutesPicker.setFormatter { formatTimePicker(it) }
                secondsPicker.setFormatter { formatTimePicker(it) }
                hoursPicker.setVisible()
                minutesPicker.setVisible()
                secondsPicker.setVisible()
            }
        }
    }

    private fun setupTimepicker() {
        setupTimepickerMinValues()
        setupTimepickerMaxValues()
    }

    private fun setupTimepickerMinValues() {
        hoursPicker.minValue = minValueTime
        minutesPicker.minValue = minValueTime
        secondsPicker.minValue = minValueTime
    }

    private fun setupTimepickerMaxValues() {
        hoursPicker.maxValue = maxValueHours
        minutesPicker.maxValue = maxValueMinutes
        secondsPicker.maxValue = maxValueSeconds
    }

    private fun initTimepickerListeners() {
        hoursPicker.apply {
            setOnValueChangedListener(hoursListener)
            setOnScrollListener(hoursListener)
        }
        minutesPicker.apply {
            setOnValueChangedListener(minutesListener)
            setOnScrollListener(minutesListener)
        }
        secondsPicker.apply {
            setOnValueChangedListener(secondsListener)
            setOnScrollListener(secondsListener)
        }
    }

    private fun getCalendar(time: String, pattern: String): Calendar {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        calendar.time = sdf.parse(time) as Date
        return calendar
    }

    public fun setInitialState(timepickerCallback: TimepickerCallback) {
        this.timepickerCallback = timepickerCallback
    }

    public fun changeTime(time: String, withCallback: Boolean, pattern: String) {
        val calendar = getCalendar(time, pattern)
        timeSetOption(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND),
            withCallback,
        )
    }

    private companion object {
        private const val MAX_VALUE_HOURS = 23
        private const val MAX_VALUE_MIN_SEC = 59
    }
}