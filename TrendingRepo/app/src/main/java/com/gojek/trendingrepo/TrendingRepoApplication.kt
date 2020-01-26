package com.gojek.trendingrepo

import android.content.Context
import android.util.Log
import com.facebook.stetho.Stetho
import com.gojek.trendingrepo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.io.PrintWriter
import java.io.StringWriter

class TrendingRepoApplication : DaggerApplication() {

    companion object {

        private var mContext: Context? = null

        fun getContext(): Context? {
            return mContext
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        //used to monitor DB
        Stetho.initializeWithDefaults(this)

        mContext = applicationContext

        Thread.setDefaultUncaughtExceptionHandler { _, exception ->
            val sw = StringWriter()
            val exceptionAsString = sw.toString()

            exception.printStackTrace(PrintWriter(sw))

            Log.e("  ---->  %s", exceptionAsString)
            Log.e("uncaughtException", ": Exception ENDS")

            //TODO: log runtime exception to remote API or Save in local directory
        }
    }
}