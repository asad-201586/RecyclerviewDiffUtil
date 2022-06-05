package com.octopi.recyclerviewdiffutil.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {
    fun createService(): ApiInterface {

        val client = OkHttpClient().newBuilder()
            .callTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
//            .addInterceptor(interceptor)
//            .authenticator(TokenAuthenticator())

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .client(client.build())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    companion object {

        private const val BASE_URL = "https://jsonplaceholder.typicode.com"

        private val instance by lazy { Api().createService() }
        fun getService() = instance
    }

}

fun apiHitter(): ApiInterface {
    return Api.getService()
}