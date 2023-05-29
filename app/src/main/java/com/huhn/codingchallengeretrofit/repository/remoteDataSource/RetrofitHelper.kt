package com.huhn.codingchallengeretrofit.repository.remoteDataSource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    val baseUrl = "https://guidebook.com/service/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            //define where to get the Json
            .baseUrl(baseUrl)
            // convert JSON object to Kotlin object
            .addConverterFactory(GsonConverterFactory.create())
            //build the Retrofit instance
            .build()
    }
}