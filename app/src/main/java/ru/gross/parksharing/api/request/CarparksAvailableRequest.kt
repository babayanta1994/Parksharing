package ru.gross.parksharing.api.request

data class CarparksAvailableRequest(
    val AppID: String,
    val ID: String,
    val Phone: String,
    val lat: Double,
    val lon: Double,
    val `when`: String
)