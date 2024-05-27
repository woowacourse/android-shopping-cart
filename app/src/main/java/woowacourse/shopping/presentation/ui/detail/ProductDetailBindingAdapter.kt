package woowacourse.shopping.presentation.ui.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.presentation.ui.UiState

@BindingAdapter("productPrice")
fun TextView.setProductPrice(cartProduct: CartProduct?) {
    cartProduct?.let {
        this.text = this.context.getString(R.string.won, it.price * it.quantity)
    }
}