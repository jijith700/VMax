package com.jijith.vmax.api

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by jijith on 12/27/17.
 */
interface WebApiListener {

    //LOGIN API
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body loginRequest: RequestBody): Observable<Response<ResponseBody>>


}