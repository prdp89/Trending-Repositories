package com.gojek.trendingrepo.binding

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.gojek.trendingrepo.R
import com.gojek.trendingrepo.datasource.entity.TrendingRepoEntity

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    if (null != url) {

        val glideUrl = GlideUrl(url)
        Glide.with(imageView.context)
            .load(glideUrl)
            .apply(RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).circleCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.place_holder)
    }
}

@BindingAdapter("customBackground")
fun bindCustomBackground(imageView: AppCompatImageView, trendingRepoEntity: TrendingRepoEntity?) {
    if (null != trendingRepoEntity?.language && null != imageView.background) {
        (imageView.background as GradientDrawable).setColor(Color.parseColor(trendingRepoEntity.languageColor))
    } else if (null != imageView.background) {
        (imageView.background as GradientDrawable).setColor(Color.BLACK)
    }
}