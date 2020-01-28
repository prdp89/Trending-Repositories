package com.gojek.trendingrepo.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gojek.trendingrepo.datasource.Database
import com.gojek.trendingrepo.datasource.dao.TrendingRepoDao
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.datasource.service.ApiInterface
import com.gojek.trendingrepo.util.InstantAppExecutors
import com.gojek.trendingrepo.util.TestUtil
import com.gojek.trendingrepo.vo.Status
import com.mindtree.igxbridge.traderapp.vo.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.internal.matchers.apachecommons.ReflectionEquals


@RunWith(JUnit4::class)
class TrendingRepositoryTest {

    private lateinit var mRepository: TrendingRepository

    private val dao = mock(TrendingRepoDao::class.java)

    private val mContext = mock(Context::class.java)

    private val mApiInterface = mock(ApiInterface::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(Database::class.java)
        Mockito.`when`(db.trendingRepoDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        mRepository = TrendingRepository(InstantAppExecutors(), mContext, mApiInterface, db)
    }

    @Test
    fun loadTrendingRepoFromNetwork() {
        val dbData = MutableLiveData<TrendingRepoEntity>()
        Mockito.`when`(dao.load("pardeep", "prdp89")).thenReturn(dbData)

        Mockito.verify(mApiInterface, Mockito.never())
            .getTrendingRepositories()
    }

    @Test
    fun searchFromDb() {
        val dbSearchResult = MutableLiveData<TrendingRepoEntity>()
        Mockito.`when`(dao.load("pp", "ss")).thenReturn(dbSearchResult)
    }

    @Test
    fun searchFromServer() {
        val repo1 = TestUtil.createRepository("pardeep", "prdp")
        val repo2 = TestUtil.createRepository("sandeep", "sharma")

        val liveData = MutableLiveData<Resource<List<TrendingRepoEntity>>>()

        val repoList = arrayListOf(repo1, repo2)
        liveData.postValue(Resource(Status.SUCCESS, repoList, "", null))
        dao.insertTrendingRepoList(repoList)
        Mockito.verify(dao).insertTrendingRepoList(repoList)

        val liveDataRepo = mRepository.getTrendingRepositories(true).value
        Assert.assertTrue(ReflectionEquals(liveDataRepo?.status).matches(Status.LOADING))
    }
}