package ru.gross.parksharing.ui.datemodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.R
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.request.CarparkDetailRequest
import ru.gross.parksharing.utils.CalendarAdapter

class DateModel : ViewModel() {
    var calendarAdapter: CalendarAdapter? = null

}