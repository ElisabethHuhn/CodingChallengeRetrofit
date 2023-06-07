package com.huhn.codingchallengeretrofit.repository.remoteDataSource

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    val baseUrl = "https://api.duckduckgo.com"

    fun getInstance(): Retrofit {
        //add logging of request/response
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

       val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(interceptor)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            //define where to get the Json
            .baseUrl(baseUrl)
            .client(httpClient.build())
            // convert JSON object to Kotlin object
            .addConverterFactory(GsonConverterFactory.create(gson))
            //build the Retrofit instance
            .build()
    }
}