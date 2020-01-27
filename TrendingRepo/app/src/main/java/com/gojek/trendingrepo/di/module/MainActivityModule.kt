package com.gojek.trendingrepo.di.module

import com.gojek.trendingrepo.view.common.InternetConnectionDialog
import com.gojek.trendingrepo.view.home.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeRepoListFragment(): RepoListFragment

    @ContributesAndroidInjector
    abstract fun contributeDialogFragment(): InternetConnectionDialog
}