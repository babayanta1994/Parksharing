package ru.gross.parksharing.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gross.parksharing.R
import ru.gross.parksharing.databinding.CalendarCellBinding
import java.text.SimpleDateFormat
import java.util.*


import android.widget.BaseAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class CalendarAdapter(
    private val mContext: Context
) : BaseAdapter() {
    private var dateArray: List<Date> = ArrayList()
    private val mDateManager: DateManager
    private val mLayoutInflater: LayoutInflater

    var pos_a: Date
    var pos_b: Date

    override fun getCount(): Int {
        return dateArray.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var calendar_cell = CalendarCellBinding.inflate(mLayoutInflater, parent, false)


        // clear styling
        calendar_cell.dateText.setTypeface(null, Typeface.NORMAL)
        calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell_transparent)


        //Gray out cells other than this month
        if (mDateManager.isCurrentMonth(dateArray[position])) {
            //Display only the date
            val dateFormat = SimpleDateFormat("d", Locale.US)
            calendar_cell.dateText.text = dateFormat.format(dateArray[position])
            if (mDateManager.isEqualOr1g2Dates(dateArray[position], Date())) {
                calendar_cell.dateText.setTextColor(Color.WHITE)
                if (pos_a.time != Date(0).time && pos_b.time != Date(0).time) {
                    if (!mDateManager.isEqualDates(pos_a,pos_b)) {
                        if (mDateManager.isBetween(dateArray[position],pos_a,pos_b)) {
                            var draw1 = R.drawable.back_cell_1
                            var draw2 = R.drawable.back_cell_1
                            var draw3 = R.drawable.round_cell
                            if (mDateManager.isEqualDates(pos_a,dateArray[position])) draw1 =
                                R.drawable.back_cell_2
                            else if (mDateManager.isEqualDates(pos_b,dateArray[position])) draw2 =
                                R.drawable.back_cell_2
                            else draw3 = R.drawable.round_cell_transparent

                            calendar_cell.dateBack1.setBackgroundResource(draw1)
                            calendar_cell.dateBack2.setBackgroundResource(draw2)
                            calendar_cell.dateText.setBackgroundResource(draw3)
                        }
                    } else {
                        if (mDateManager.isEqualDates(pos_a,dateArray[position])) {
                            calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell)
                        }
                    }
                } else {
                    if (mDateManager.isEqualDates(pos_a,dateArray[position]) ) {
                        calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell)
                    }
                }

            } else {
                calendar_cell.dateText.setTextColor(Color.GRAY)
                calendar_cell.root.isEnabled = false
            }

        } else {
            calendar_cell.dateText.setTextColor(Color.parseColor("#E0E0E0"))
            calendar_cell.root.isEnabled = false
            calendar_cell.dateText.text = ""//convertView.setBackgroundColor(Color.LTGRAY)
        }




        return calendar_cell.root
    }

    fun clickOnDate(position: Int, view: View) {

        //clear all fields
//                    clearAdapter(parent)
//                    //check current position in range
        if (pos_a.time == Date(0).time) {
            pos_a.time = dateArray[position].time
        } else if (pos_b.time == Date(0).time) {
            pos_b.time = dateArray[position].time
        } else {
            pos_a.time = dateArray[position].time
            pos_b.time = Date(0).time
        }
        //fill grid dates
//
        if (pos_a.time != Date(0).time && pos_b.time != Date(0).time) {
            if (pos_a.time > pos_b.time) {
                val tmp_pos = pos_a.time
                pos_a.time = pos_b.time
                pos_b.time = tmp_pos
            }
            if (pos_a.time != pos_b.time) {
                duration = getDaysStr(
                    TimeUnit.DAYS.convert(
                        (pos_b.time - pos_a.time),
                        TimeUnit.MILLISECONDS
                    )
                )
                durationClickable = false

            } else {
                duration = "1 час"
                durationClickable = true
            }
        } else {
            if (pos_a.time != Date(0).time) {
                duration = "1 час"
                durationClickable = true
            }
        }
        notifyDataSetChanged()

    }


    fun getDaysStr(pos: Long): String {
        return if (pos in 5..20) {
            "$pos дней"
        } else if (pos % 10 == 1L) {
            "$pos день"
        } else if (pos % 10 in 2..4) {
            "$pos дня"
        } else {
            "$pos дней"
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return dateArray[position]
    }

    var duration: String = ""
    var durationClickable: Boolean = true


    private val _title = MutableLiveData<String>().apply {
       value =""
    }
    val title: LiveData<String> = _title


    fun changeTitle(){
        val format = SimpleDateFormat("LLLL yyyy")
        _title.value = format.format(mDateManager.mCalendar.time).replaceFirstChar { it.uppercase() }
    }

    //Next month display
    fun nextMonth() {
        mDateManager.nextMonth()
        dateArray = mDateManager.days
        changeTitle()
        notifyDataSetChanged()
    }

    //Previous month display
    fun prevMonth() {
        if (!mDateManager.isCurrentMonth(Date())) {
            mDateManager.prevMonth()
            dateArray = mDateManager.days
            changeTitle()
            notifyDataSetChanged()
        }
    }

    fun selectMonth(time: Long) {
        mDateManager.selectMonth( Date(time))
        dateArray = mDateManager.days
        changeTitle()
        notifyDataSetChanged()
    }


    init {
        mLayoutInflater = LayoutInflater.from(mContext)
        mDateManager = DateManager()
        dateArray = mDateManager.days
        changeTitle()
        pos_a = Date(0L)
        pos_b = Date(0L)

    }
}

