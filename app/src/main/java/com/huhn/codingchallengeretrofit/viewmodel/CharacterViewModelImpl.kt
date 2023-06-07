package com.huhn.codingchallengeretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huhn.codingchallengeretrofit.TAG
import com.huhn.codingchallengeretrofit.model.Character
import com.huhn.codingchallengeretrofit.model.RelatedTopic
import com.huhn.codingchallengeretrofit.repository.CharacterRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModelImpl() : ViewModel()
{
    var repo : CharacterRepositoryImpl = CharacterRepositoryImpl()

    var _characterRelatedTopics = MutableLiveData<List<RelatedTopic>>()
    val characterRelatedTopics : LiveData<List<RelatedTopic>>
        get() = _characterRelatedTopics

    fun fetchCharacters()  {
        repo.getCharacters(characterCallbackHandler = characterCallbackHandler)
    }

    fun findPosition(patternName: String) : Int {
        var counter = 0
        _characterRelatedTopics.value?.forEach { relatedTopic ->
            if (relatedTopic.FirstURL == patternName) return counter
            counter++
        }
        return -1
    }
    fun findRelatedTopic(position: Int): RelatedTopic? {
        return _characterRelatedTopics.value?.get(position)
    }

    //Create the callback object that will parse the response and
    // actually fill the gPatternData variable
    //Define the code to execute upon request return
    val characterCallbackHandler = object : Callback<Character> {
        override fun onFailure(call: Call<Character>, t: Throwable) {
            Log.e(TAG, "characterCallBackHandler: Failure Return from network call", t)
            // TODO: Somehow inform the user of the error
        }

        override fun onResponse(
            call: Call<Character>,
            response: Response<Character>
        ) {
            Log.d(TAG, "Response Received: ${response.toString()}")

            try {
                val responseCode = response.code()
                // debug information
                val responseMessage = response.message()
                val responseIsSuccessful = response.isSuccessful
                val responseHeaders = response.headers()
                val responseBody = response.body()
                val responseErrorBody = response.errorBody()
                val responseDebug =
                    "code = $responseCode \n isSuccessful = $responseIsSuccessful\n message = $responseMessage\n headers = $responseHeaders\n body = $responseBody\n error = $responseErrorBody"
                Log.d(TAG, responseDebug)
                // end debug info

                when (responseCode) {
                    200 -> {
                        _characterRelatedTopics.value = response.body()?.RelatedTopics ?: listOf()
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

    fun filterCharacterNameFromUrl(urlString: String) : String {
        return urlString.substringAfter(
            delimiter = "https://duckduckgo.com/",
            missingDelimiterValue = "Some Name"
        ).replace(
            oldChar = '_',
            newChar = ' ',
            ignoreCase = true
        ).replace(
            oldValue = "%22",
            newValue = "\"",
            ignoreCase = true
        )
    }

}