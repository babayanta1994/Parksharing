package ru.gross.parksharing.ui.general_screen.my_cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCarsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my cars Fragment"
    }
    val text: LiveData<String> = _text
}