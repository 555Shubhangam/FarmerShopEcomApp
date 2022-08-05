package com.farmershop.ui.base

import android.app.Application
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatDelegate
import com.farmershop.utils.AppSession

class MyApp : Application() {

    companion object {
        lateinit var application: Application


        fun getInstance(): Application {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        AppSession.init(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
       // AppDatabase.invoke(applicationContext)
    }


}