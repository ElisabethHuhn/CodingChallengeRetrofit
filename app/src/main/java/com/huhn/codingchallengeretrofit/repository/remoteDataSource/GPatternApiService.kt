package com.huhn.codingchallengeretrofit.repository.remoteDataSource

import com.huhn.codingchallengeretrofit.model.GPattern
import retrofit2.Call
import retrofit2.http.GET


interface GPatternApiService {
    @GET("upcomingGuides/")
    fun fetchGPattern() : Call<GPattern>
}