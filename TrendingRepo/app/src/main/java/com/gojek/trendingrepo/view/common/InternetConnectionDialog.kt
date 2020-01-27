package com.gojek.trendingrepo.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.databinding.DialogInternetConnectionBinding
import com.gojek.trendingrepo.utils.ConnectionUtils
import com.gojek.trendingrepo.utils.autoCleared
import com.gojek.trendingrepo.view.home.RepoListViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class InternetConnectionDialog @Inject constructor() :
    DaggerDialogFragment() {

    private var mViewModel: RepoListViewModel? = null

    private var mBinding by autoCleared<DialogInternetConnectionBinding>()

    private var mOnConfirmListener: OnConfirmListener? = null

    override fun getTheme(): Int {
        return R.style.AppTheme_Dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this dialog
        val dataBinding = DataBindingUtil.inflate<DialogInternetConnectionBinding>(
            inflater,
            R.layout.dialog_internet_connection,
            container,
            false
        )
        mBinding = dataBinding

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewEvents()
    }

    fun setViewModel(viewModel: RepoListViewModel) {
        this.mViewModel = viewModel
    }

    private fun observeViewEvents() {
        mBinding.btnRetry.setOnClickListener {
            if (ConnectionUtils.isNetworkAvailable(context)) {
                mOnConfirmListener?.onConfirmClick()
                mViewModel?.fetchTrendingRepositories(true)
                this.dismiss()
            }
        }
    }

    fun setOnConfirmListener(onConfirmListener: OnConfirmListener): InternetConnectionDialog {
        mOnConfirmListener = onConfirmListener
        return this
    }

    interface OnConfirmListener {
        fun onConfirmClick()
    }
}