package ru.gross.parksharing.components

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import ru.gross.parksharing.R

class ClusterPlace (context: Context, txt:String)  : LinearLayout(context){
    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cluster, this)
        setText(txt)
    }

    fun setText(text: String) {
        var pinTextView: TextView = findViewById(R.id.cluster_sz)
        pinTextView.text = text
    }


}