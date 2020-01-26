package com.gojek.trendingrepo.utils

import android.os.SystemClock
import androidx.collection.ArrayMap

import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 * We can use SharedPreference if RateLimiting if required on each App Launch basis.
 */
class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val mTimestamps = ArrayMap<KEY, Long>()
    private val mTimeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = mTimestamps[key]
        val now = now()
        if (null == lastFetched) {
            mTimestamps[key] = now
            return true
        }
        if (now - lastFetched > mTimeout) {
            mTimestamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        mTimestamps.remove(key)
    }
}
