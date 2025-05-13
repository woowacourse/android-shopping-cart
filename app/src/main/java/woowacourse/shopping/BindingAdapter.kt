package woowacourse.shopping

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imgRes")
fun setImageViewResource(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}