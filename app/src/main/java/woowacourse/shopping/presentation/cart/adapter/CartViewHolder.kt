package woowacourse.shopping.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.Order

class CartViewHolder(
    private val binding: ItemCartBinding,
    cartItemClickListener: CartItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartItemClickListener = cartItemClickListener
    }

    fun bind(order: Order) {
        binding.order = order
    }
}
