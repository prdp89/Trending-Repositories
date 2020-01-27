package com.gojek.trendingrepo.binding

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.gojek.trendingrepo.R

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    if (null != url) {

        val glideUrl = GlideUrl(url)
        Glide.with(imageView.context)
            .load(glideUrl)
            .apply(RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).circleCrop())
            .placeholder(attachPlaceHolder(imageView))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.place_holder)
    }
}

private fun attachPlaceHolder(imageView: ImageView): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 6000f
    circularProgressDrawable.centerRadius = 6000f
    circularProgressDrawable.colorFilter = PorterDuffColorFilter(
        ContextCompat.getColor(imageView.context, R.color.colorPrimary), PorterDuff.Mode.SRC
    )
    circularProgressDrawable.start()
    return circularProgressDrawable
}