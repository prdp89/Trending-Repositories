package com.gojek.trendingrepo.datasource.service

import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("repositories")
    fun getTrendingRepositories(): Call<List<TrendingRepoEntity>>
}