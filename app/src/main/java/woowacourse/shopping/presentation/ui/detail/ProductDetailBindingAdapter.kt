package woowacourse.shopping.presentation.ui.detail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.ui.UiState

@BindingAdapter("productNameState")
fun TextView.setProductNameByState(uiState: UiState<CartProduct>) {
    if (uiState is UiState.Success) this.text = uiState.data.name
}

@BindingAdapter("productPriceState")
fun TextView.setProductPriceByState(uiState: UiState<CartProduct>) {
    if (uiState is UiState.Success) {
        this.text =
            this.context.getString(
                R.string.won,
                uiState.data.price,
            )
    }
}

@BindingAdapter("productUrlState")
fun ImageView.setProductUrlByState(uiState: UiState<CartProduct>) {
    if (uiState is UiState.Success) {
        Glide.with(this.context)
            .load((uiState.data.imgUrl))
            .into(this)
    }
}
