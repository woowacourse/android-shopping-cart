package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.product.CartItem

class CartViewHolder(
    private val binding: CartItemBinding,
    viewModel: CartViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewModel = viewModel
    }

    fun bind(cartItem: CartItem) {
        binding.product = cartItem.product
        binding.cartItem = cartItem
    }
}
