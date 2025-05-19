package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartProduct

class CartViewHolder(
    private val binding: ItemCartBinding,
    onClickHandler: OnClickHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClickHandler = onClickHandler
    }

    fun bind(cartProduct: CartProduct) {
        binding.cartProduct = cartProduct
    }

    interface OnClickHandler {
        fun onRemoveCartProductClick(id: Int)
    }
}
