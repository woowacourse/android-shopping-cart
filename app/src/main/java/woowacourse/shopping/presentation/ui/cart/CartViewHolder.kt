package woowacourse.shopping.presentation.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.presentation.viewmodel.cart.CartViewModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    onClickHandler: OnClickHandler,
    viewModel: CartViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
        binding.viewModel = viewModel
    }

    fun bind(product: CartProduct) {
        binding.product = product
    }

    interface OnClickHandler {
        fun onRemoveCartProductClick(id: Int)
    }
}
