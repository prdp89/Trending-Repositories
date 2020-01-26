package com.gojek.trendingrepo.datasource.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity

@Dao
interface TrendingRepoDao {
    @Query("SELECT * FROM trending_repo")
    fun loadTrendingRepos(): LiveData<List<TrendingRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingRepo(trendingRepoEntity: TrendingRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)
}