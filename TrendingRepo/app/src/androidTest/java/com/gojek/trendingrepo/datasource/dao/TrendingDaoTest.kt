package com.gojek.trendingrepo.datasource.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gojek.trendingrepo.utils.LiveDataTestUtil.getValue
import com.gojek.trendingrepo.utils.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndRead() {
        val repo = TestUtil.createRepository("pardeepsharma", "prdp89")

        db.trendingRepoDao().insertTrendingRepo(repo)

        val loaded = getValue(db.trendingRepoDao().load("pardeepsharma", "prdp89"))

        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.name, CoreMatchers.`is`("prdp89"))
        MatcherAssert.assertThat(loaded.author, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.author, CoreMatchers.`is`("pardeepsharma"))

    }
}