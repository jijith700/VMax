package com.jijith.vmax.api

import com.google.gson.GsonBuilder
import com.jijith.vmax.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jijith on 12/26/17.
 */
@Module
class ApiService {

    @Inject
    constructor()

    var retrofit: Retrofit? = null

    //get retrofit instance
    @Singleton
    @Provides
    fun getRetrofit(): WebApiListener {
        if (retrofit == null) {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpClient.Builder()
                            .connectTimeout(50, TimeUnit.SECONDS)
                            .readTimeout(2, TimeUnit.MINUTES)
                            .writeTimeout(5, TimeUnit.MINUTES)
                            .build())
                    .build()
        }

        return retrofit!!.create(WebApiListener::class.java)
    }
}