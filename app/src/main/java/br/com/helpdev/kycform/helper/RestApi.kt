package br.com.helpdev.kycform.helper

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RestApi {


    @Headers("Content-Type: application/json")
    @POST("save_sms.php")
    fun addUser(@Body userData: DataRequest): Call<JsonObject>




}