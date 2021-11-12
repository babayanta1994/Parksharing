package ru.gross.parksharing.components

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ru.gross.parksharing.R

class Placemark(context: Context,txt:String)  : LinearLayout(context){
    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.parking_place, this)
        setText(txt)


    }



    constructor(context: Context,txt: String,isTaped:Boolean) : this(context,txt) {
        var pinLogoView: ImageView = findViewById(R.id.pinLogoView)

        pinLogoView.setImageDrawable(resources.getDrawable(
                if(isTaped){
                    R.drawable.ic_selected_location
                }
                else{
                    R.drawable.ic_location
                }
                ,context.theme
        ))

    }

    fun setText(text: String) {
        var pinTextView: TextView  = findViewById(R.id.pinTextView)
        pinTextView.text = text
    }


}