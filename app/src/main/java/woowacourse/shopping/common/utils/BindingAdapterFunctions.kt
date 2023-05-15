package woowacourse.shopping.common.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadPicture")
fun ImageView.loadPicture(picture: String) {
    Glide.with(context)
        .load(picture)
        .centerCrop()
        .into(this)
}
