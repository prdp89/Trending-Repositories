package com.gojek.trendingrepo.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gojek.trendingrepo.AppExecutors
import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.utils.autoCleared
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

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mAdapter by autoCleared<RepoListAdapter>()

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
        initRecyclerView()
        observeViewEvents()

        mViewModel.fetchTrendingRepositories(false)
    }

    //endregion

    //region Private Methods

    private fun initRecyclerView() {
        val adapter = RepoListAdapter(mAppExecutors) {
        }
        this.rv_repo_list.adapter = adapter
        this.mAdapter = adapter
    }

    private fun observeViewEvents() {
        swipe_refresh_layout.setOnRefreshListener {
            mViewModel.fetchTrendingRepositories(true)
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun subscribeToLiveData() {
        mViewModel.mRepositoryLiveData?.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                val result = it.data as List<TrendingRepoEntity>
                mAdapter.submitList(result)

                mAdapter.notifyDataSetChanged()
            } else if (it.status == Status.ERROR) {
                //TODO: Error logging here and Related UI
            }
        })
    }
    //endregion
}
