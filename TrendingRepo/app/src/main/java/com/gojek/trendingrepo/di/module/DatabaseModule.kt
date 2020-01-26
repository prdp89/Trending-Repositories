package com.gojek.trendingrepo.di.module

import android.app.Application
import androidx.room.Room
import com.gojek.trendingrepo.datasource.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "trending_repo_db")
            .build()
}