package com.huhn.codingchallengeretrofit.repository.remoteDataSource

import com.huhn.codingchallengeretrofit.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

const val SIMPSONS_URL = "/?q=simpsons+characters&format=json/"
const val WIRE_URL     = "/?q=the+wire+characters&format=json"

interface CharacterApiService {
    @Headers("Content-Type: application/json")
    @GET(SIMPSONS_URL)
    fun fetchSimpsonsCharacter() : Call<Character>

    @Headers("Content-Type: application/json")
    @GET(WIRE_URL)
    fun fetchWireCharacter() : Call<Character>
}