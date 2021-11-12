package ru.gross.parksharing.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Fee (

    @SerializedName("unit") var unit : Int,
    @SerializedName("price") var price : Int,
    @SerializedName("name") var name : String

):Serializable