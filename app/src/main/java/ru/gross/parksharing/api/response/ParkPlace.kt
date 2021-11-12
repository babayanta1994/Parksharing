package ru.gross.parksharing.api.response

data class ParkPlace(
    var capacity: Int? = null,
    var id: Int? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var name: String? = null,
    var num_vacant: Int? = null,
    var tz: String? = null
)