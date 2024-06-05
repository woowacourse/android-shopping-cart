package woowacourse.shopping.presentation.ui.shopping

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("productUrl")
fun ImageView.setProductUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("productCounterVisibility")
fun View.setProductCounterVisibility(quantity: Int?) {
    this.isVisible = (quantity != null && quantity > 0)
}

@BindingAdapter("productCartVisibility")
fun View.setProductCartVisibility(quantity: Int?) {
    this.isVisible = (quantity == null)
}
