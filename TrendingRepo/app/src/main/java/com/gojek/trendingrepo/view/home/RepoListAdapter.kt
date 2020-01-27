package com.gojek.trendingrepo.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.gojek.trendingrepo.AppExecutors
import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.databinding.ItemTrendingRepoBinding
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity
import com.gojek.trendingrepo.vo.databoundadapter.DataBoundListAdapter


class RepoListAdapter(
    appExecutors: AppExecutors,
    private val callback: ((TrendingRepoEntity) -> Unit)?
) :
    DataBoundListAdapter<TrendingRepoEntity,
            ItemTrendingRepoBinding>(mAppExecutors = appExecutors,
        mDiffCallback = object : DiffUtil.ItemCallback<TrendingRepoEntity>() {
            override fun areItemsTheSame(
                oldItem: TrendingRepoEntity,
                newItem: TrendingRepoEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrendingRepoEntity,
                newItem: TrendingRepoEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }) {

    private var previousExpandedPosition = Int.MIN_VALUE
    private var mExpandedPosition = Int.MIN_VALUE

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTrendingRepoBinding {

        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_repo,
            parent,
            false
        )
    }

    override fun bind(
        binding: ItemTrendingRepoBinding,
        item: TrendingRepoEntity,
        position: Int
    ) {
        binding.entity = item

        val isExpanded = position == mExpandedPosition
        toggleViewGroup(position, isExpanded, binding)

        binding.root.setOnClickListener {
            callback?.invoke(binding.entity!!)

            mExpandedPosition = if (isExpanded) Int.MIN_VALUE else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    private fun toggleViewGroup(
        position: Int,
        isExpanded: Boolean,
        binding: ItemTrendingRepoBinding
    ) {
        binding.group.visibility = if (isExpanded) View.VISIBLE else View.GONE
        binding.root.isActivated = isExpanded

        if (isExpanded)
            previousExpandedPosition = position
    }
}