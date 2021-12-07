package ru.gross.parksharing.ui.general_screen.pay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PayViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pay methods Fragment"
    }
    val text: LiveData<String> = _text
}