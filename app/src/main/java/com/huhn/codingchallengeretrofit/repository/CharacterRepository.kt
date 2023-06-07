package com.huhn.codingchallengeretrofit.repository

import com.huhn.codingchallengeretrofit.BuildConfig
import com.huhn.codingchallengeretrofit.model.Character
import com.huhn.codingchallengeretrofit.repository.remoteDataSource.CharacterApiService
import com.huhn.codingchallengeretrofit.repository.remoteDataSource.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback


interface CharacterRepository {
    fun getCharacters(characterCallbackHandler: Callback<Character>)
//    fun findGPattern(gPatternId: String) : GPattern?
//
//    fun findRoute(gPatternId: String) : Route?
}

class CharacterRepositoryImpl() : CharacterRepository {
    /*
     * use init block to create RetroFit instance and GPatternApiService instance
     * we'll use the gPatternApi instance to make RetroFit Calls
     */

    //remote data source variables
    private val characterApi: CharacterApiService

    init {
        /*
         * Use RetrofitHelper to create the instance of Retrofit
         * Then use this instance to create an instance of the API
         */
        characterApi = RetrofitHelper.getInstance().create(CharacterApiService::class.java)
    }

    override fun getCharacters(characterCallbackHandler: Callback<Character>) {
        //TODO investigate setting up a Flow in the callback, and updating the view-model that way
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            var characterCall = characterApi.fetchWireCharacter()
            if (BuildConfig.CHARACTER_TYPE_STRING.contains("Simpsons"))
                characterCall = characterApi.fetchSimpsonsCharacter()

            //Initiate the remote call, with the passed callback to handle the remote response
            characterCall.enqueue(characterCallbackHandler)
        }
    }



}