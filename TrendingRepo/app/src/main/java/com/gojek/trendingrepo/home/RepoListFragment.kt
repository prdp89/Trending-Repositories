package com.gojek.trendingrepo.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.vo.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.repo_list_fragment.*
import javax.inject.Inject

class RepoListFragment : DaggerFragment() {

    companion object {
        fun newInstance() = RepoListFragment()
    }

    @Inject
    lateinit var mViewModel: RepoListViewModel

    //region Fragment Overridden Methods
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()

        mViewModel.fetchTrendingRepositories(false)
    }
    //endregion

    //region Private Methods
    private fun subscribeToLiveData() {
        mViewModel.mRepositoryLiveData?.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS) {
                val result = it.data as List<TrendingRepoEntity>

            } else if (it.status == Status.ERROR) {
                //TODO: Error logging here and Related UI
            }
        })
    }
    //endregion
}
