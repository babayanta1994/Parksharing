package ru.gross.parksharing.api.request

data class CarparkDetailRequest(
        val AppID: String,
        val ID: String,
        val Phone: String,
        val carpark_id: Int,
)