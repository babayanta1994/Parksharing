package ru.gross.parksharing.api.services

import retrofit2.Call
import ru.gross.parksharing.api.response.ParkPlace
import retrofit2.http.POST

import retrofit2.http.Body
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.request.CarparkDetailRequest
import ru.gross.parksharing.request.CarparksAvailableRequest


interface RetrofitServices {

//    @FormUrlEncoded
//    @POST("api/activation")
//    fun activation(
//        @Header("Accept-Language") locale: String?,
//        @Field("number") apartmentNumber: String?,
//        @Field("activation_code") activationCode: String?
//    ): Observable<Response<UserModel?>?>?
//
//    @POST
//    fun postClaim(
//        @Header("Authorization") api_token: String?,
//        @Body claimJSON: HashMap<String?, Any?>?,
//        @Url endpointUrl: String?
//    ): Observable<Response<ClaimModel?>?>?

    @POST("carparks/available")
    fun getCarparksAvailable(@Body carparksAvailableRequest: CarparksAvailableRequest): Call<MutableList<ParkPlace>>

    @POST("carpark")
    fun getCarparkById(@Body carparksAvailableRequest: CarparkDetailRequest): Call<ParkDetail>

}