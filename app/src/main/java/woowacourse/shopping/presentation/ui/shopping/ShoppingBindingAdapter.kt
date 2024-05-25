package woowacourse.shopping.presentation.ui.shopping

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.detail.ProductDetailViewModel
import woowacourse.shopping.presentation.ui.detail.setProductSrc

@BindingAdapter("productUrl")
fun ImageView.setProductUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

@BindingAdapter("productCounterVisibility")
fun View.setProductCounterVisibility(quantity: Int?){
    this.isVisible = (quantity != null && quantity > 0)
}

@BindingAdapter("productCartVisibility")
fun View.setProductCartVisibility(quantity: Int?){
    this.isVisible = (quantity == null)
}
