package ru.gross.parksharing.utils

import java.text.SimpleDateFormat
import java.util.*


class DateManager {
    var mCalendar: Calendar//Keep current state

    //Calculate the total number of squares to be displayed in GridView

    //Calculate the number of days for the previous month displayed on the calendar for the current month

    //Restore state
    //Get the elements of the month
    val days: List<Date>
        get() {
            //Keep current state
            val startDate = mCalendar.time

            //Calculate the total number of squares to be displayed in GridView
            val count = (weeks+1) * 7

            //Calculate the number of days for the previous month displayed on the calendar for the current month
            mCalendar[Calendar.DATE] = 0
            val dayOfWeek = mCalendar[Calendar.DAY_OF_WEEK] - 2
            mCalendar.add(Calendar.DATE, -dayOfWeek)
            val days: MutableList<Date> = ArrayList()
            for (i in 0 until count) {
                days.add(mCalendar.time)
                mCalendar.add(Calendar.DATE, 1)
            }

            //Restore state
            mCalendar.time = startDate
            return days
        }

    val months: List<Date>
        get() {
            //Keep current state
            val startDate = mCalendar.time

            val count = 12
            mCalendar[Calendar.DAY_OF_YEAR] = 1
            val months: MutableList<Date> = ArrayList()
            for (i in 1..count) {
                months.add(mCalendar.time)
                mCalendar.add(Calendar.MONTH, 1)
            }

            //Restore state
            mCalendar.time = startDate
            return months
        }
    //Check if it is this month
    fun isCurrentMonth(date: Date?): Boolean {
        val format = SimpleDateFormat("yyyy.MM", Locale.US)
        val currentMonth = format.format(mCalendar.time)
        return if (currentMonth == format.format(date)) {
            true
        } else {
            false
        }
    }


    fun isCurrentYear(date: Date?): Boolean {
        val format = SimpleDateFormat("yyyy", Locale.US)
        val currentYear = format.format(mCalendar.time)
        return if (currentYear == format.format(date)) {
            true
        } else {
            false
        }
    }


    fun isEqualOr1g2Dates(date1: Date?, date2: Date?): Boolean {
        val format = SimpleDateFormat("yyyy.MM.d", Locale.US)
        return format.format(date2) == format.format(date1) || date1!!.time>=date2!!.time
    }
    fun isEqualDates(date1: Date?, date2: Date?): Boolean {
        val format = SimpleDateFormat("yyyy.MM.d", Locale.US)
        return format.format(date2) == format.format(date1)
    }

    fun isEqualMonthes(date1: Date?,date2: Date?): Boolean {
        val format = SimpleDateFormat("yyyy.MM", Locale.US)
        return format.format(date2) == format.format(date1) || date1!!.time>=date2!!.time
    }

    //Get the number of weeks
    val weeks: Int
        get() = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH)

    //Get the day of the week
    fun getDayOfWeek(date: Date?): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.DAY_OF_WEEK]
    }

    //To the next month
    fun nextMonth() {
        mCalendar.add(Calendar.MONTH, 1)
    }

    //To the previous month
    fun prevMonth() {
        mCalendar.add(Calendar.MONTH, -1)
    }


    //To the next month
    fun nextYear() {
        mCalendar.add(Calendar.YEAR, 1)
    }

    //To the previous month
    fun prevYear() {
        mCalendar.add(Calendar.YEAR, -1)
    }

    fun selectMonth(time:Date){
        mCalendar.time = time
    }

    init {
        mCalendar = Calendar.getInstance()
    }
}