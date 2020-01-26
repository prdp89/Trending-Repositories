package com.gojek.trendingrepo.di

import com.gojek.trendingrepo.TrendingRepoApplication
import com.gojek.trendingrepo.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<TrendingRepoApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<TrendingRepoApplication>
}