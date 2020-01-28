package com.gojek.trendingrepo.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gojek.trendingrepo.datasource.Database
import com.gojek.trendingrepo.datasource.service.ApiInterface
import com.gojek.trendingrepo.repository.TrendingRepository
import com.gojek.trendingrepo.util.InstantAppExecutors
import com.gojek.trendingrepo.view.home.RepoListViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class RepoListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mContext = mock(Context::class.java)

    private val mApiInterface = mock(ApiInterface::class.java)

    private lateinit var trendingRepository: TrendingRepository

    private lateinit var trendingRepoViewModel: RepoListViewModel

    @Before
    fun init() {
        val db = mock(Database::class.java)
        trendingRepository = TrendingRepository(InstantAppExecutors(), mContext, mApiInterface, db)
        trendingRepoViewModel = RepoListViewModel(trendingRepository)
    }

    @Test
    fun testNull() {
        MatcherAssert.assertThat(
            trendingRepoViewModel.mRepositoryLiveData,
            CoreMatchers.notNullValue()
        )
    }
}