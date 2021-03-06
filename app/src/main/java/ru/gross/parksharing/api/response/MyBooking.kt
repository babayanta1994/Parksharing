package ru.gross.parksharing.api.response

data class MyBooking(
    val car_regplate: String,
    val carpark_id: Int,
    val carpark_name: String,
    val addr: String,
    val datetime_from: String,
    val datetime_till: String,
    val fee: Double,
    val lat: Double,
    val lon: Double,
    val feeplan: String,
    val id: Int,
    val slot: String,
    val status: String
)