package ru.gross.parksharing.api.request

data class AddCarRequest(
    val AppID: String,
    val ID: String,
    val Phone: String,
    val car_regplate:String,
    val note:String
)