package com.gojek.trendingrepo.datasource.service

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gojek.trendingrepo.AppExecutors
import com.gojek.trendingrepo.vo.Status
import com.mindtree.igxbridge.traderapp.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResourceRetrofit<ResultType, RequestType> @MainThread
protected constructor(private val mAppExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.setValue(
                        Resource.success(
                            "",
                            newData,
                            null
                        )
                    )
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val retrofitCall = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.removeSource(dbSource)
        retrofitCall?.enqueue(object : Callback<RequestType> {
            override fun onFailure(call: Call<RequestType>, t: Throwable) {
                onFetchFailed()
                mAppExecutors.mainThread().execute {
                    result.addSource(dbSource)
                    { requestType ->
                        run {
                            result.value = t.message?.let { Resource.error(it, requestType) }
                        }
                    }
                }
            }

            override fun onResponse(call: Call<RequestType>, response: Response<RequestType>) {
                if (response.isSuccessful && response.body() != null) {
                    mAppExecutors.diskIO().execute {
                        saveCallResult(processResponse(response.body() as RequestType))
                        mAppExecutors.mainThread().execute {
                            result.addSource(loadFromDb()) { resultType ->
                                result.setValue(
                                    Resource.success(
                                        "",
                                        resultType,
                                        null
                                    )
                                )
                            }
                        }
                    }
                } else {
                    result.value =
                        Resource(Status.ERROR, null, response.errorBody().toString(), null)
                }
            }

        })
    }

    protected abstract fun onFetchFailed()

    fun asLiveData(): MediatorLiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: RequestType): RequestType {
        return response
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): Call<RequestType>?
}
