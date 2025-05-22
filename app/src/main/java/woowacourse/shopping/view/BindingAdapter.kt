package woowacourse.shopping.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageUrl")
fun setImageUrl(
    view: ImageView,
    imageUrl: String?,
) {
    Glide
        .with(view.context)
        .load(imageUrl)
        .placeholder(R.drawable.product_image_loading)
        .fallback(R.drawable.product_image_error)
        .error(R.drawable.product_image_error)
        .into(view)
}
