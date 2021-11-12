package ru.gross.parksharing.request

data class CarparkDetailRequest(
        val AppID: String,
        val ID: String,
        val Phone: String,
        val carpark_id: Int,
)