package woowacourse.shopping.presentation.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.RecentProductItem
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("bindLoadImage")
fun ImageView.loadImage(imgUrl: String?) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}

@BindingAdapter("bindSetVisibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("lastProduct", "currentProduct")
fun View.setLastProductVisibility(
    lastProduct: RecentProductItem?,
    currentProduct: ProductListItem.ShoppingProductItem?,
) {
    if (currentProduct != null) {
        if (lastProduct == null || lastProduct.productId == currentProduct.id) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("bindTextFormattedCurrency")
fun TextView.textFormattedCurrency(price: Long) {
    text = price.currency(context)
}

fun Long.currency(context: Context): String {
    return when (Locale.getDefault().country) {
        Locale.KOREA.country -> context.getString(R.string.price_format_kor, this)
        else -> NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
    }
}
