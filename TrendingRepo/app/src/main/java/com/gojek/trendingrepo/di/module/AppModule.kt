package com.gojek.trendingrepo.di.module

import android.app.Application
import android.content.Context
import com.gojek.trendingrepo.TrendingRepoApplication
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [AndroidSupportInjectionModule::class
        , ActivityBuildersModule::class
        , NetworkModule::class
        , DatabaseModule::class]
)
abstract class AppModule {

    @Binds
    abstract fun provideApplicationContext(app: Application): Context

    @Binds
    abstract fun provideApplication(app: TrendingRepoApplication): Application
}