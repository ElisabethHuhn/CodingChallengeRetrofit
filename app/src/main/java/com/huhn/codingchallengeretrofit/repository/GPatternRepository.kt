package com.huhn.codingchallengeretrofit.repository

import com.huhn.codingchallengeretrofit.model.GPattern
import com.huhn.codingchallengeretrofit.repository.remoteDataSource.GPatternApiService
import com.huhn.codingchallengeretrofit.repository.remoteDataSource.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Callback


interface GPatternRepository {
    fun getGPatterns(gPatternCallbackHandler: Callback<GPattern>)
//    fun findGPattern(gPatternId: String) : GPattern?
//
//    fun findRoute(gPatternId: String) : Route?
}

class GPatternRepositoryImpl() : GPatternRepository {
    /*
     * use init block to create RetroFit instance and GPatternApiService instance
     * we'll use the gPatternApi instance to make RetroFit Calls
     */

    //remote data source variables
    private val gPatternApi: GPatternApiService

    init {
        /*
         * Use RetrofitHelper to create the instance of Retrofit
         * Then use this instance to create an instance of the API
         */
        gPatternApi = RetrofitHelper.getInstance().create(GPatternApiService::class.java)
    }

    override fun getGPatterns(gPatternCallbackHandler: Callback<GPattern>) {
        //TODO investigate setting up a Flow in the callback, and updating the view-model that way
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val gPatternsAndRoutesCall = gPatternApi.fetchGPattern()
            //Initiate the remote call, with the passed callback to handle the remote response
            gPatternsAndRoutesCall.enqueue(gPatternCallbackHandler)
        }
    }



}