package ru.gross.parksharing.api.request

data class DeleteCarRequest(
    val AppID: String,
    val ID: String,
    val Phone: String,
    val car_regplate:String
)