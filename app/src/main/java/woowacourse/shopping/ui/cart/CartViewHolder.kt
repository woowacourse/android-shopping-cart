package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.domain.product.Product

class CartViewHolder(
    private val binding: CartItemBinding,
    cartClickListener: CartClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartClickListener = cartClickListener
    }

    fun bind(item: Product) {
        binding.product = item
    }
}
