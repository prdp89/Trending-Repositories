package com.gojek.trendingrepo.home

import android.view.LayoutInflater
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

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTrendingRepoBinding {

        val binding = DataBindingUtil.inflate<ItemTrendingRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_repo,
            parent,
            false
        )

        binding.root.setOnClickListener {

        }
        return binding
    }


    override fun bind(
        binding: ItemTrendingRepoBinding,
        item: TrendingRepoEntity,
        position: Int
    ) {
        binding.entity = item
    }
}