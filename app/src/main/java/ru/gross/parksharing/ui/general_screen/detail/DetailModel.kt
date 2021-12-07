package ru.gross.parksharing.ui.general_screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.api.request.CarparkDetailRequest

class DetailModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is how it Fragment"
    }
    private val _parkDetail = MutableLiveData<ParkDetail>().apply {
        value = ParkDetail("","","","","","", listOf(), listOf())
    }
    var carpark_id: Int? = 0
    val text: LiveData<String> = _text
    val parkDetail: LiveData<ParkDetail> = _parkDetail
    private var mService: RetrofitServices = Common.retrofitService


    fun getDetailCarpark() {
        carpark_id?.let {
            val carparkDetailRequest =
                CarparkDetailRequest(
                    Phone = "+79999999999",
                    AppID = "A1B1-C2D2-E3F3-G4H4-I5J5",
                    ID = "K1L1-M2N2-O3P3-Q4R4-S5T5",
                    carpark_id = it
                )
            println("carpark_id "+it)
            mService.getCarparkById(carparkDetailRequest).enqueue(object :
                Callback<ParkDetail> {
                override fun onResponse(call: Call<ParkDetail>, response: Response<ParkDetail>) {
                    val park = response.body() as ParkDetail?
                    _parkDetail.value = park
                    //showparkDetail(park)
                }

                override fun onFailure(call: Call<ParkDetail>, t: Throwable) {

                }
            })
        }

    }

}