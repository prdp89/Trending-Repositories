package com.gojek.trendingrepo.utils

import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity

object TestUtil {

    fun createTrendingRepository(
        count: Int,
        owner: String,
        name: String
    ): List<TrendingRepoEntity> {
        return (0 until count).map {
            createRepository(
                author = owner + it,
                name = name + it
            )
        }
    }

    fun createRepository(author: String, name: String) =
        createTrendingRepo(
            id = TrendingRepoEntity.UNKNOWN_ID,
            name = name,
            author = author
        )

    private fun createTrendingRepo(id: Int, name: String, author: String) = TrendingRepoEntity(
        id = id,
        name = name,
        author = author,
        stars = 3,
        avatar = "",
        description = "",
        forks = 5,
        url = "",
        language = "English",
        languageColor = ""
    )
}
