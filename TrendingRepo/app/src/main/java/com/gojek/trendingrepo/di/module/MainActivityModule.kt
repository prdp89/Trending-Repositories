package com.gojek.trendingrepo.di.module

import com.gojek.trendingrepo.home.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeRepoListFragment(): RepoListFragment
}