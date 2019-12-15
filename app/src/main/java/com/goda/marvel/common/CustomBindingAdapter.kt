package com.goda.marvel.common

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.goda.marvel.R
import com.goda.marvel.model.Thumbnail
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object CustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("typeFace")
    fun setTypeFace(view: View?, type: String) {
        if (view != null && !TextUtils.isEmpty(type)) {
            (view as TextView).typeface = Typeface.createFromAsset(view.context.assets, type)
        }
    }

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView?, thumbnail: Thumbnail?) {
        thumbnail?.let {
            if (view != null && !TextUtils.isEmpty(it.path)) {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.loading_animation)
                requestOptions.error(R.drawable.ic_broken_image)
                Glide.with(view.context).load("${it.path}.jpg")
                        .apply(requestOptions)
                        .into(view)
            }
        }

    }
}