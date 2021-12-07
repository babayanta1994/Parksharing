package ru.gross.parksharing.api.request

data class GetBookingRequest(
    val AppID: String,
    val ID: String,
    val Phone: String,
    val car_regplate:String?=null,
    val datetime_from: String?=null,
    val datetime_till: String?=null
)