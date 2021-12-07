package ru.gross.parksharing.ui.waitotp_screen

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.response.ConfirmRegisterResponse
import ru.gross.parksharing.api.response.RegisterResponse
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.db.entity.User
import ru.gross.parksharing.api.request.ConfirmRegisterRequest
import ru.gross.parksharing.api.request.RegisterRequest
import java.util.*

import java.util.concurrent.Callable
import java.util.concurrent.Executors


class WaitOtpModel(application: Application) : AndroidViewModel(application) {

    var phone: String? = null
    var uniqueID: String? = null
    val _sec = MutableLiveData<Int>().apply { value = 60 }

    val llwait = MutableLiveData<Int>().apply { value = View.VISIBLE }
    val llretry = MutableLiveData<Int>().apply { value = View.GONE }


    val isProgress = MutableLiveData<Boolean>().apply { value = false }
    var isConfirmed = MutableLiveData<Boolean>().apply { value = false }




    private val database: ParkDatabase

    init {
        isConfirmed.value = false
        database = ParkDatabase.getDatabase(getApplication())
    }



    fun updateUser(user: User) {
        val callable: Callable<Any> = object : Callable<Any> {
            override fun call() {
                database.userDao().deleteAll()
                database.userDao().insert(user)
            }
        }
        Executors.newSingleThreadExecutor().submit(callable)
    }


    fun getUser(): LiveData<User> {
       return database.userDao().getUser()
    }


    fun updateUUID(context: Context) {
        val sharedPref = context.getSharedPreferences(
            "GET_PARK", Context.MODE_PRIVATE
        )
        uniqueID = sharedPref.getString("UUID", "-")!!
        if (uniqueID == "-") {
            uniqueID = UUID.randomUUID().toString()
            with(sharedPref.edit()) {
                putString("UUID", uniqueID)
                apply()
            }
        }
    }

    fun tickSeconds() {
        llwait.value = View.VISIBLE
        llretry.value = View.GONE
        Thread {
            var sec = 60
            while (sec > 0) {
                sec--
                _sec.postValue(sec)
                Thread.sleep(1000)
            }
            llwait.postValue(View.GONE)
            llretry.postValue(View.VISIBLE)
        }.start()
    }


    fun getLoginInfo() {
        if (phone != null && uniqueID != null) {
            tickSeconds()
            var mService = Common.retrofitService
            val req = RegisterRequest(phone!!, uniqueID!!)
            Log.e("WaitOtpActivity", ">>>>> req ${req}")
            mService.register(req)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        var resp: RegisterResponse = response.body() as RegisterResponse
                        Log.e("WaitOtpActivity", ">>>>> onResponse ${resp}")

                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("WaitOtpActivity", ">>>>> onFailure")
                    }

                })
        }


    }

    fun confirmReg(code: Int) {
        isProgress.value = true
        var mService = Common.retrofitService
        val req = ConfirmRegisterRequest(phone!!, uniqueID!!, code)
        Log.e("WaitOtpActivity", ">>>>> req ${req}")
        mService.confirmRegister(req)
            .enqueue(object : Callback<ConfirmRegisterResponse> {
                override fun onResponse(
                    call: Call<ConfirmRegisterResponse>,
                    response: Response<ConfirmRegisterResponse>
                ) {
                    Log.e(
                        "WaitOtpActivity",
                        ">>>>> onResponse ${response.body()} ${response.code()}"
                    )
                    if (response.code().toString().equals("200")) {
                        var resp: ConfirmRegisterResponse =
                            response.body() as ConfirmRegisterResponse
                        updateUser(User(phone!!, uniqueID!!, resp.ID!!))
                        isConfirmed.value = true
                    }
                    else{
                        isConfirmed.value = false
                    }
                    isProgress.value = false

                }

                override fun onFailure(call: Call<ConfirmRegisterResponse>, t: Throwable) {
                    Log.e("WaitOtpActivity", ">>>>> onFailure")
                    isProgress.value = false
                    isConfirmed.value = false
                }

            })
    }

}