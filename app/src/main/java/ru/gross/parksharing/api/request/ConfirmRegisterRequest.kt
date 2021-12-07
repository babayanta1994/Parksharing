package ru.gross.parksharing.api.request

data class ConfirmRegisterRequest(
    val Phone: String,
    val AppID: String,
    val OTP: Int,
)