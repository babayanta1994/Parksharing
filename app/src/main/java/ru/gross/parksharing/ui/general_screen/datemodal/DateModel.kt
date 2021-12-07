package ru.gross.parksharing.ui.general_screen.datemodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.utils.CalendarAdapter
import java.util.*

class DateModel : ViewModel() {
    var calendarAdapter: CalendarAdapter? = null
    private val _dateBegin = MutableLiveData<Date>().apply {
        value = Date()
    }
    val dateBegin: LiveData<Date> = _dateBegin
    private val _dateEnd = MutableLiveData<Date>().apply {
        value = Date()
    }
    val dateEnd: LiveData<Date> = _dateEnd

}