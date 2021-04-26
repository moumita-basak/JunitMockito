package com.infosys.junitmockito.modelnew

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName


data class ItemRow(
    @SerializedName("title")
    val title: Any,
    @SerializedName("description")
    val description: Any,
    @SerializedName("imageHref")
    val imageHref: Any

    )

@BindingAdapter("imageHref")
fun loadImage(view: ImageView, imageHref: Int?) {
    Glide.with(view.getContext())
        .load(imageHref)
        .into(view)
}
