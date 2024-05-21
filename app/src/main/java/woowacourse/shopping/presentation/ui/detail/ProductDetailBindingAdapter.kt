package woowacourse.shopping.presentation.ui.detail

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.cart.CartViewModel

@BindingAdapter("productName")
fun TextView.setProductName(viewModel: ProductDetailViewModel) {
    if (viewModel.products.value is UiState.Success) this.text = (viewModel.products.value as UiState.Success<Product>).data.name
}

@BindingAdapter("productPrice")
fun TextView.setProductPrice(viewModel: ProductDetailViewModel) {
    if (viewModel.products.value is UiState.Success) this.text = this.context.getString(R.string.won, (viewModel.products.value as UiState.Success<Product>).data.price)
}

@BindingAdapter("productSrc")
fun ImageView.setProductSrc(viewModel: ProductDetailViewModel) {
    if(viewModel.products.value is UiState.Success) Glide.with(this)
        .load((viewModel.products.value as UiState.Success<Product>).data.imgUrl)
        .into(this)
}