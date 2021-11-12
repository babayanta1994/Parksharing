package ru.gross.parksharing.api.common

import ru.gross.parksharing.api.client.RetrofitClient
import ru.gross.parksharing.api.services.RetrofitServices


object Common {
    private val BASE_URL = "https://online.elsaccess.com/parksharing/v1/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}