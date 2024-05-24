package woowacourse.shopping.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    Glide.with(context)
        .load(imgUrl)
        .placeholder(R.drawable.img_odooong)
        .error(R.drawable.ic_error_24)
        .into(this)
}

@BindingAdapter("isVisible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
