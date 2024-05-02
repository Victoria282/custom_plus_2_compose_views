package com.example.custom_plus_2_compose_views.date_picker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import net.intersvyaz.library.core_common_ui.databinding.CustomDatePickerLayoutBinding
import com.example.custom_plus_2_compose_views.date_picker.model.DatePicker
import com.example.custom_plus_2_compose_views.timepicker.CustomPickerChangeListener
import net.intersvyaz.library.core_common_ui.timepicker.CustomPickerChangeListener
import java.text.SimpleDateFormat
import java.util.*

public class CustomDatePicker @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(ctx, attrs) {

    private val binding: CustomDatePickerLayoutBinding = CustomDatePickerLayoutBinding.inflate(
        LayoutInflater.from(ctx), this, true
    )

    private val calendar: Calendar = Calendar.getInstance()

    private val locale = Locale.getDefault()
    private val monthFormatter = SimpleDateFormat(MONTH_PATTERN, locale)
    private val yearFormatter = SimpleDateFormat(YEAR_PATTERN, locale)

    private val monthList: ArrayList<String> = arrayListOf()
    private val yearsList: ArrayList<String> = arrayListOf()

    private val yearListener = object : CustomPickerChangeListener() {
        override fun onValueChanged(value: Int) {
            isCorrectDate(yearIndex = value, monthIndex = binding.month.value)
        }
    }

    private val monthListener = object : CustomPickerChangeListener() {
        override fun onValueChanged(value: Int) {
            isCorrectDate(monthIndex = value, yearIndex = binding.year.value)
        }
    }

    public fun setupDatePicker(countOfDisplayedYears: Int = DEFAULT_COUNT_OF_DISPLAYED_YEARS) {
        setupYears(countOfDisplayedYears)
        setUpMonths()
        setupMinValues()
        setupMaxValues()
        setupListeners()
        setupSelectedValues()
        setupDisplayedValues()
    }

    public fun getDate(): DatePicker = DatePicker(
        month = (binding.month.value + 1).toString(),
        year = yearsList[binding.year.value]
    )

    private fun setupYears(countOfDisplayedYears: Int) {
        for (i in 1 - countOfDisplayedYears..0) {
            val calendar = i.calendarOfSelectedYear()
            val year: String = calendar.time.formatYearName()
            yearsList.add(year)
        }
    }

    private fun setUpMonths() {
        val calendar = Calendar.getInstance()
        for (i in 0..11) {
            calendar[Calendar.MONTH] = i
            val month: String = calendar.time.formatMonthName()
            monthList.add(month)
        }
    }

    private fun Int.calendarOfSelectedYear(): Calendar {
        val newCalendar = Calendar.getInstance()
        newCalendar.add(Calendar.YEAR, this)
        return newCalendar
    }

    private fun setupMinValues() = with(binding) {
        year.minValue = FIRST_INDEX
        month.minValue = FIRST_INDEX
    }

    private fun setupMaxValues() = with(binding) {
        year.maxValue = yearsList.size - 1
        month.maxValue = monthList.size - 1
    }

    private fun setupListeners() = with(binding) {
        year.setOnScrollListener(yearListener)
        year.setOnValueChangedListener(yearListener)
        month.setOnScrollListener(monthListener)
        month.setOnValueChangedListener(monthListener)
    }

    private fun setupSelectedValues() = with(binding) {
        year.value = indexOfCurrentYear()
        month.value = indexOfCurrentMonth()
    }

    private fun setupDisplayedValues() = with(binding) {
        month.displayedValues = monthList.toTypedArray()
        year.displayedValues = yearsList.toTypedArray()
    }

    private fun Date.formatMonthName(): String {
        val month: String = monthFormatter.format(this)
        return month.substring(0, 1).uppercase() + month.substring(1)
    }

    private fun Date.formatYearName(): String = yearFormatter.format(this.time)

    private fun indexOfCurrentMonth(): Int {
        val currentMonth = calendar.time.formatMonthName()
        return monthList.indexOf(currentMonth)
    }

    private fun indexOfCurrentYear(): Int {
        val currentYear = calendar.time.formatYearName()
        return yearsList.indexOf(currentYear)
    }

    private fun isCorrectDate(
        monthIndex: Int,
        yearIndex: Int
    ) {
        val currentMonthIndex = indexOfCurrentMonth()
        val maxYearIndex = binding.year.maxValue

        if (monthIndex > currentMonthIndex && yearIndex == maxYearIndex) {
            binding.month.value = currentMonthIndex
        }
    }

    private companion object {
        private const val DEFAULT_COUNT_OF_DISPLAYED_YEARS = 2
        private const val FIRST_INDEX = 0
        private const val MONTH_PATTERN: String = "LLLL"
        private const val YEAR_PATTERN: String = "YYYY"
    }
}