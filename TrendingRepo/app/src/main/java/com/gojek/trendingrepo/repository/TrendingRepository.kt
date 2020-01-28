package com.gojek.trendingrepo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojek.trendingrepo.AppExecutors
import com.gojek.trendingrepo.datasource.Database
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.datasource.service.ApiInterface
import com.gojek.trendingrepo.datasource.service.NetworkBoundResourceRetrofit
import com.gojek.trendingrepo.utils.ConnectionUtils
import com.gojek.trendingrepo.utils.OpenForTesting
import com.gojek.trendingrepo.utils.RateLimiter
import com.gojek.trendingrepo.vo.Resource
import retrofit2.Call
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class TrendingRepository @Inject constructor(
    private val mAppExecutors: AppExecutors
    , private val mContext: Context
    , private val mApiInterface: ApiInterface
    , private val mAppDatabase: Database
) {

    companion object {
        private const val KEY_RANGE_FETCH = "keyTrendingRepoFetch"
    }

    private val mRateLimiter = RateLimiter<String>(2, TimeUnit.HOURS)

    fun getTrendingRepositories(isForcedFetch: Boolean): MutableLiveData<Resource<List<TrendingRepoEntity>>> {
        return object :
            NetworkBoundResourceRetrofit<List<TrendingRepoEntity>, List<TrendingRepoEntity>>(
                mAppExecutors
            ) {

            override fun onFetchFailed() {
                mRateLimiter.reset(KEY_RANGE_FETCH)
            }

            override fun saveCallResult(item: List<TrendingRepoEntity>) {

                mAppDatabase.trendingRepoDao().deleteOldRepos()

                if (item.isNotEmpty()) {
                    mAppDatabase.trendingRepoDao().insertTrendingRepoList(item)
                }
            }

            override fun shouldFetch(data: List<TrendingRepoEntity>?): Boolean {
                return (data == null || data.isEmpty() || mRateLimiter.shouldFetch(KEY_RANGE_FETCH) || isForcedFetch)
                        && ConnectionUtils.isNetworkAvailable(mContext)
            }

            override fun loadFromDb(): LiveData<List<TrendingRepoEntity>> {
                return mAppDatabase.trendingRepoDao().loadTrendingRepos()
            }

            override fun createCall(): Call<List<TrendingRepoEntity>> {
                return mApiInterface.getTrendingRepositories()
            }
        }.asLiveData()
    }
}