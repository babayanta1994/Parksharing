package ru.gross.parksharing.ui.general_screen.my_booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyBookingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my booking Fragment"
    }
    val text: LiveData<String> = _text
}