package woowacourse.shopping.data.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("app:imageUrl")
fun loadImage(
    view: ImageView,
    url: String?,
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("app:price")
fun setPrice(
    view: TextView,
    price: Long,
) {
    view.text = view.context.getString(R.string.price_format, price)
}

@BindingAdapter("app:selectedBasedOn")
fun setSelectedBasedOn(
    button: AppCompatButton,
    isSelected: Boolean,
) {
    button.isSelected = isSelected
}

@BindingAdapter("app:viewVisibility")
fun setViewVisibility(
    view: View,
    isVisible: Boolean,
) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:totalQuantityVisibility")
fun setTotalQuantityVisibility(
    textView: TextView,
    totalQuantity: Int,
) {
    textView.visibility = if (totalQuantity > 0) View.VISIBLE else View.GONE
}
