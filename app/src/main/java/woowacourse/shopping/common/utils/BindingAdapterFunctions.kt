package woowacourse.shopping.common.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadPicture")
fun ImageView.loadPicture(picture: String) {
    Glide.with(context)
        .load(picture)
        .centerCrop()
        .into(this)
}

@BindingAdapter("isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}
