package ru.gross.parksharing.components

import android.content.DialogInterface

import android.content.Context
import android.view.LayoutInflater
import android.view.View

import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import ru.gross.parksharing.R


class DurationPicker(context: Context) : AlertDialog.Builder(context) {
    var currentValue: Int = 1
    var currentDuration: String = "1 час"
    val inflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(R.layout.duration_dialog, null)
    var picker:NumberPicker=NumberPicker(context)
    init {
        this.setView(view)
        picker = view.findViewById(R.id.picker) as NumberPicker
        picker.setMinValue(1)
        picker.setMaxValue(24)

        picker.displayedValues = generateSequence(1, { (it + 1).takeIf { it <= 24 } }).map {
            if (it in 5..20) {
                "$it часов"
            } else if (it % 10 == 1) {
                "$it час"
            } else {
                "$it часа"
            }
        }.toList().toTypedArray()
        picker.value = currentValue
    }


}