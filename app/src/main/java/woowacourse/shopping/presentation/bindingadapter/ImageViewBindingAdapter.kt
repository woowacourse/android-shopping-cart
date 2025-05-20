package woowacourse.shopping.presentation.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("imageUrl")
fun setLoadImage(
    view: ImageView,
    url: String?,
) {
    Glide
        .with(view)
        .load(url)
        .placeholder(R.drawable.ic_loading)
        .fallback(R.drawable.ic_delete)
        .error(R.drawable.ic_delete)
        .into(view)
}
