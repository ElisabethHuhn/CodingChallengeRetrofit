package com.huhn.codingchallengeretrofit.application

import android.app.Application

class CodingChallengeApplication  : Application() {
    override fun onCreate() {
        super.onCreate()

//        GlobalContext.startKoin {
//            androidLogger()
//            androidContext(this@CodingChallengeApplication)
//            modules(listOf(koinModule))
//        }
    }
}