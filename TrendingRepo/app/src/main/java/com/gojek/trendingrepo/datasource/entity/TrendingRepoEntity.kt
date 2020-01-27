package com.gojek.trendingrepo.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "trending_repo"
)
data class TrendingRepoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var author: String,
    var avatar: String,
    var description: String,
    var forks: Int,
    var name: String,
    var stars: Int,
    var url: String,
    var language: String? = null,
    var languageColor: String? = null
)