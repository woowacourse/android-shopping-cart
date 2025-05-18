package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.product.catalog.ProductUiModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(productUiModel: ProductUiModel) {
        Glide
            .with(this)
            .load(productUiModel.imageUrl)
            .placeholder(R.drawable.iced_americano)
            .fallback(R.drawable.iced_americano)
            .error(R.drawable.iced_americano)
            .into(this)
    }
}
