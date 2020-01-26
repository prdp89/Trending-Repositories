package com.gojek.trendingrepo

import android.util.Log
import com.gojek.trendingrepo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.io.PrintWriter
import java.io.StringWriter

class TrendingRepoApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { t, exception ->
            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()
            Log.e("  ---->  %s", exceptionAsString)
            Log.e("uncaughtException", ": Exception ENDS")

            //TODO: log runtime exception to remote API or Save in local directory
        }
    }
}