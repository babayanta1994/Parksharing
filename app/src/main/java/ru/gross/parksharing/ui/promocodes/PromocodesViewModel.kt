package ru.gross.parksharing.ui.promocodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PromocodesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is promocodes Fragment"
    }
    val text: LiveData<String> = _text
}