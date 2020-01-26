package com.gojek.trendingrepo.di.module

import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.TrendingRepoApplication
import com.gojek.trendingrepo.datasource.service.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TrendingRepoApplication.getContext()?.getString(R.string.app_base_url)!!)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
        ApiInterface::class.java
    )
}