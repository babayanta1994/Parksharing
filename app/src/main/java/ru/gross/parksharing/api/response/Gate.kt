package ru.gross.parksharing.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Gate (

    @SerializedName("label") var label : String,
    @SerializedName("lat") var lat : Double,
    @SerializedName("lon") var lon : Double,
    @SerializedName("photo_url") var photo_url : String

):Serializable
