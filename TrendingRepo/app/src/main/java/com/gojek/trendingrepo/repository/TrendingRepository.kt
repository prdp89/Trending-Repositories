package com.gojek.trendingrepo.repository

import android.content.Context
import com.gojek.trendingrepo.AppExecutors
import javax.inject.Inject

class TrendingRepository @Inject constructor(
    private val mAppExecutors: AppExecutors,
    private val mContext: Context
) {

}