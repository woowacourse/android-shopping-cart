package woowacourse.shopping.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun Context.imageUrlToSrc(
    url: String,
    view: ImageView,
) = Glide.with(this).load(url).into(view)
