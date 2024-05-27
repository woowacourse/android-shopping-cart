package woowacourse.shopping.presentation.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import woowacourse.shopping.domain.CartProduct

@BindingAdapter("productPrice")
fun TextView.setProductPrice(cartProduct: CartProduct?) {
    cartProduct?.let {
        this.text = this.context.getString(R.string.won, it.price * it.quantity)
    }
}
