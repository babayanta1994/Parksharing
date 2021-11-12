package ru.gross.parksharing.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.response.ParkPlace
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.request.CarparksAvailableRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is map Fragment"
    }
    val text: LiveData<String> = _text


}