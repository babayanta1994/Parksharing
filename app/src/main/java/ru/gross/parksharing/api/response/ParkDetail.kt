package ru.gross.parksharing.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ParkDetail (

    @SerializedName("name") var name : String,
    @SerializedName("operator") var operator : String,
    @SerializedName("address") var address : String,
    @SerializedName("phone") var phone : String,
    @SerializedName("www") var www : String,
    @SerializedName("photo_url") var photoUrl : String,
    @SerializedName("fee") var fee : List<Fee>,
    @SerializedName("gates") var gates:List<Gate>

):Serializable