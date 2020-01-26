package com.gojek.trendingrepo.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gojek.trendingrepo.datasource.dao.TrendingRepoDao
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity

@Database(
    entities = [TrendingRepoEntity::class]
    , version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao
}