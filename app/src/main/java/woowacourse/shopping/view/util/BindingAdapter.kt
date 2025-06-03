package woowacourse.shopping.view.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.RecentProduct

@BindingAdapter("imageUrl")
fun ImageView.setImage(image: String?) {
    image ?: return
    Glide
        .with(this.context)
        .load(image)
        .placeholder(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .into(this)
}

@BindingAdapter(value = ["lastViewedProduct", "currentProductId"], requireAll = true)
fun View.hideIfSameProduct(
    lastViewed: RecentProduct?,
    currentId: Long?,
) {
    visibility =
        if (lastViewed == null || lastViewed.product.id == currentId) {
            View.GONE
        } else {
            View.VISIBLE
        }
}
