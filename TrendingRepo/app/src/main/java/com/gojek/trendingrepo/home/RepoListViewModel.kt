package com.gojek.trendingrepo.home

import androidx.lifecycle.ViewModel
import com.gojek.trendingrepo.repository.TrendingRepository
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val mTrendingRepository: TrendingRepository) :
    ViewModel() {
}
