package ru.gross.parksharing.api.services

import retrofit2.Call
import retrofit2.http.POST

import retrofit2.http.Body
import ru.gross.parksharing.api.response.*
import ru.gross.parksharing.api.request.*


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


    @POST("reg")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("confirmreg")
    fun confirmRegister(@Body registerRequest: ConfirmRegisterRequest): Call<ConfirmRegisterResponse>


    @POST("carparks/available")
    fun getCarparksAvailable(@Body carparksAvailableRequest: CarparksAvailableRequest): Call<MutableList<ParkPlace>>

    @POST("carpark")
    fun getCarparkById(@Body carparksAvailableRequest: CarparkDetailRequest): Call<ParkDetail>


    @POST("mycars")
    fun getMyCars(@Body myCarsRequest: MyCarsRequest): Call<MutableList<MyCar>>


    @POST("mycars/del")
    fun deleteCar(@Body deleteCarRequest: DeleteCarRequest): Call<StatusCarResponse>


    @POST("mycars/mod")
    fun addCar(@Body addCarRequest: AddCarRequest): Call<StatusCarResponse>



    @POST("mybookings/current")
    fun getCurrentBooking(@Body getBookingRequest: GetBookingRequest): Call<MutableList<MyBooking>>


}