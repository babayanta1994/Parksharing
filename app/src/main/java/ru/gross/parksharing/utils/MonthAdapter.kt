package ru.gross.parksharing.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.gross.parksharing.R
import ru.gross.parksharing.databinding.CalendarCellBinding
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import java.util.concurrent.TimeUnit


class MonthAdapter(
    private val mContext: Context
) : BaseAdapter() {
    private var dateArray: List<Date> = ArrayList()
    private val mDateManager: DateManager
    private val mLayoutInflater: LayoutInflater


    override fun getCount(): Int {
        return dateArray.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var calendar_cell = CalendarCellBinding.inflate(mLayoutInflater, parent, false)


        // clear styling
        calendar_cell.dateText.setTypeface(null, Typeface.NORMAL)
        calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell_transparent)


        //Gray out cells other than this month
        if (mDateManager.isCurrentYear(dateArray[position])) {
            //Display only the date
            val dateFormat = SimpleDateFormat("LLL")
            calendar_cell.dateText.text = dateFormat.format(dateArray[position])

            if(mDateManager.isEqualMonthes(dateArray[position],Date())){
                calendar_cell.dateText.setTextColor(Color.WHITE)
            } else {
                calendar_cell.dateText.setTextColor(Color.GRAY)
                calendar_cell.root.isEnabled = false
            }
//            if (mDateManager.isEqualDates(dateArray[position],Date())) {
//                calendar_cell.dateText.setTextColor(Color.WHITE)
//                if (pos_a.time != Date(0).time && pos_b.time != Date(0).time) {
//                    if (pos_a.time != pos_b.time) {
//                        if (dateArray[position].time in pos_a.time..pos_b.time) {
//                            var draw1 = R.drawable.back_cell_1
//                            var draw2 = R.drawable.back_cell_1
//                            var draw3 = R.drawable.round_cell
//                            if (pos_a.time == dateArray[position].time) draw1 =
//                                R.drawable.back_cell_2
//                            else if (pos_b.time == dateArray[position].time) draw2 =
//                                R.drawable.back_cell_2
//                            else draw3 = R.drawable.round_cell_transparent
//
//                            calendar_cell.dateBack1.setBackgroundResource(draw1)
//                            calendar_cell.dateBack2.setBackgroundResource(draw2)
//                            calendar_cell.dateText.setBackgroundResource(draw3)
//                        }
//                    } else {
//                        if (pos_a.time == dateArray[position].time) {
//                            calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell)
//                        }
//                    }
//                } else {
//                    if (pos_a.time == dateArray[position].time) {
//                        calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell)
//                    }
//                }
//
//            } else {
//                calendar_cell.dateText.setTextColor(Color.GRAY)
//                calendar_cell.root.isEnabled = false
//            }

        } else {
            calendar_cell.dateText.setTextColor(Color.parseColor("#E0E0E0"))
            calendar_cell.root.isEnabled = false
            calendar_cell.dateText.text = ""//convertView.setBackgroundColor(Color.LTGRAY)
        }




        return calendar_cell.root
    }

    fun clickOnDate(position: Int, view: View): Long {
        return dateArray[position].time
    }


    fun getDaysStr(pos: Long): String {
        return if (pos in 5..20) {
            "$pos дней"
        } else if (pos % 10 == 1L) {
            "$pos день"
        } else if(pos%10 in 2..4){
            "$pos дня"
        }else{
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
        val format = SimpleDateFormat("yyyy")
        _title.value = format.format(mDateManager.mCalendar.time)
    }


    //Next month display
    fun nextYear() {
        mDateManager.nextYear()
        dateArray = mDateManager.months
        changeTitle()
        notifyDataSetChanged()
    }

    //Previous month display
    fun prevYear() {
        if (!mDateManager.isCurrentYear(Date())) {
            mDateManager.prevYear()
            dateArray = mDateManager.months
            changeTitle()
            notifyDataSetChanged()
        }
    }


    init {
        mLayoutInflater = LayoutInflater.from(mContext)
        mDateManager = DateManager()
        dateArray = mDateManager.months
        changeTitle()

    }
}




/*

class MonthAdapter(
    context: Context?,
    days: ArrayList<Date?>?
) :
    ArrayAdapter<Date?>(context!!, R.layout.calendar_cell, days!!) {
    // for view inflation
    private val inflater: LayoutInflater


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // day in question
        var view: View? = view
        val calendar = Calendar.getInstance()
        val date = getItem(position)
        calendar.time = date
        var monName = SimpleDateFormat("LLL").format(date)

        val day = calendar[Calendar.DATE]
        val month = calendar[Calendar.MONTH]
        val year = calendar[Calendar.YEAR]

        // today
        val today = Date()
        val calendarToday = Calendar.getInstance()
        calendarToday.time = today
        Log.e(this.javaClass.simpleName, ">>>> ${calendarToday[Calendar.DATE]}>${day}")

        var calendar_cell = CalendarCellBinding.inflate(inflater, parent, false)


        // clear styling
        calendar_cell.dateText.setTypeface(null, Typeface.NORMAL)
        calendar_cell.dateText.setTextColor(Color.WHITE)
        calendar_cell.dateText.setBackgroundResource(R.drawable.round_cell_transparent)
        if (month < calendarToday[Calendar.MONTH] || year != calendarToday[Calendar.YEAR]) {
            // if this day is outside current month, grey it out
            calendar_cell.dateText.setTextColor(Color.parseColor("#E0E0E0"))
            calendar_cell.root.isEnabled = false
        } else if (month == calendarToday[Calendar.MONTH] && year == calendarToday[Calendar.YEAR]) {
                calendar_cell.dateText.setBackgroundResource(R.drawable.semiround_cell)
        }


        calendar_cell.dateText.text = monName
                //calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString()

        // inflate item if it does not exist yet
        if (view == null) view = calendar_cell.root

        // set text

        return view
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}*/