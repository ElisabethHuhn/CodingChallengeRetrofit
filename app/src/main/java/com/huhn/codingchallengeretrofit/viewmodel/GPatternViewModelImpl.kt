package com.huhn.codingchallengeretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huhn.codingchallengeretrofit.model.Data
import com.huhn.codingchallengeretrofit.model.GPattern
import com.huhn.codingchallengeretrofit.repository.GPatternRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GPatternViewModelImpl() : ViewModel()
{
    var isSorted = false

    var repo : GPatternRepositoryImpl = GPatternRepositoryImpl()

    var _gPatternData = MutableLiveData<List<Data>>()
    val gPatternData : LiveData<List<Data>>
        get() = _gPatternData


    fun getGPatterns()  {
        repo.getGPatterns(gPatternCallbackHandler)
    }

    fun findPosition(patternName: String) : Int {
        var counter = 0
        _gPatternData.value?.forEach { patternData ->
            if (patternData.name == patternName) return counter
            counter++
        }
        return -1
    }
    fun findGPattern(position: Int): Data? {
        return _gPatternData.value?.get(position)
    }

    //Create the callback object that will parse the response and
    // actually fill the gPatternData variable
    //Define the code to execute upon request return
    val gPatternCallbackHandler = object : Callback<GPattern> {
        val TAG = "gPatternCallBackHandler"

        override fun onFailure(call: Call<GPattern>, t: Throwable) {
            Log.e(TAG, "Failure Return from network call", t)
            // TODO: Somehow inform the user of the error
        }

        override fun onResponse(
            call: Call<GPattern>,
            response: Response<GPattern>
        ) {
            Log.d(TAG, "Response Received: ${response.toString()}")

            try {
                val responseCode = response.code()
                // debug information
                val responseMessage = response.message()
                val responseIsSuccessful = response.isSuccessful
                val responseHeaders = response.headers()
                val responseErrorBody = response.errorBody()
                val responseDebug =
                    "code = $responseCode, isSuccessful = $responseIsSuccessful, message = $responseMessage, headers = $responseHeaders, error = $responseErrorBody"
                Log.d(TAG, responseDebug)
                // end debug info

                when (responseCode) {
                    200 -> {
                        _gPatternData.value = response.body()?.data ?: listOf()
                    }
                    else -> {
                        // TODO: inform the user about the error
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Exception caught accessing response ${e.message}")
            }
        }
    }

    fun setGPatterns() {
        if (isSorted) setSortByGPatternName() //setSortedGPatterns()
        else setUnsortedGPatterns()
    }

    fun setUnsortedGPatterns() {
        //just get remote gPatterns again
        getGPatterns()
    }

    fun setSortByGPatternName() {
        val sortedList = _gPatternData.value?.sortedBy { data: Data -> data.name } ?: listOf()
        _gPatternData.value = sortedList
    }
}