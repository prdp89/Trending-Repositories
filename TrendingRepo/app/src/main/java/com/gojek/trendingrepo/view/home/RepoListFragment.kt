package com.gojek.trendingrepo.view.home

import android.app.Dialog
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
import com.gojek.trendingrepo.utils.AppUtils
import com.gojek.trendingrepo.utils.ConnectionUtils
import com.gojek.trendingrepo.utils.autoCleared
import com.gojek.trendingrepo.view.common.InternetConnectionDialog
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
    lateinit var mInternetConnectionDialog: InternetConnectionDialog

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mAdapter by autoCleared<RepoListAdapter>()

    private var mDialog: Dialog? = null

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

        mDialog = AppUtils.showLoadingDialog(context)

        subscribeToLiveData()
        initRecyclerView()
        observeViewEvents()

        if (ConnectionUtils.isNetworkAvailable(context)) {
            mViewModel.fetchTrendingRepositories(false)
        } else {
            mDialog?.dismiss()
            showNoConnectionDialog()
        }
    }

    //endregion

    //region Private Methods

    private fun initRecyclerView() {
        val adapter = RepoListAdapter(mAppExecutors) {
            //Added for Espresso Test
            //Toast.makeText(context, "On Item Click : " + it.name, Toast.LENGTH_SHORT).show()
        }
        this.rv_repo_list.adapter = adapter
        this.mAdapter = adapter
    }

    private fun observeViewEvents() {
        swipe_refresh_layout.setOnRefreshListener {
            //Added for Espresso Test
            //Toast.makeText(context, "On Refresh Called", Toast.LENGTH_SHORT).show()

            if (ConnectionUtils.isNetworkAvailable(context)) {
                mDialog?.show()
                mViewModel.fetchTrendingRepositories(true)
            } else {
                showNoConnectionDialog()
            }
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun subscribeToLiveData() {
        mViewModel.mRepositoryLiveData?.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                val result = it.data as List<TrendingRepoEntity>
                mAdapter.submitList(result)

                mAdapter.notifyDataSetChanged()
                mDialog?.dismiss()
            } else if (it.status == Status.ERROR) {
                mDialog?.dismiss()
                showNoConnectionDialog()
            }
        })
    }

    private fun showNoConnectionDialog() {
        mInternetConnectionDialog.isCancelable = false
        mInternetConnectionDialog.setViewModel(mViewModel)

        mInternetConnectionDialog.setOnConfirmListener(object :
            InternetConnectionDialog.OnConfirmListener {
            override fun onConfirmClick() {
                mDialog?.show()
            }
        })

        mInternetConnectionDialog.show(
            activity?.supportFragmentManager!!, InternetConnectionDialog::class.simpleName
        )
    }

    //endregion
}