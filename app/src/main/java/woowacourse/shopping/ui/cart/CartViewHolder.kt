package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.cart.CartProduct

class CartViewHolder(
    private val binding: CartItemBinding,
    cartClickListener: CartClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartClickListener = cartClickListener
    }

    fun bind(item: CartProduct) {
        binding.cartProduct = item
    }
}
