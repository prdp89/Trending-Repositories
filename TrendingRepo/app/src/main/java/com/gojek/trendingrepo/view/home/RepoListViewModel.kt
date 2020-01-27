package com.gojek.trendingrepo.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.repository.TrendingRepository
import com.mindtree.igxbridge.traderapp.vo.Resource
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val mTrendingRepository: TrendingRepository) :
    ViewModel() {

    private val mTriggerApiData: MutableLiveData<Boolean> = MutableLiveData()

    val mRepositoryLiveData: LiveData<Resource<List<TrendingRepoEntity>>>? = Transformations
        .switchMap(mTriggerApiData) {
            this.mTrendingRepository.getTrendingRepositories(it)
        }

    fun fetchTrendingRepositories(isRefresh: Boolean) {
        mTriggerApiData.postValue(isRefresh)
    }
}
