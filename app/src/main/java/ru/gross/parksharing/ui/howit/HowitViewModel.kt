package ru.gross.parksharing.ui.howit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HowitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is how it Fragment"
    }
    val text: LiveData<String> = _text
}