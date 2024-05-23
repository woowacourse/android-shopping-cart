package woowacourse.shopping.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.uistate.Order

class CartViewHolder(
    private val binding: ItemCartBinding,
    cartItemClickListener: CartItemClickListener,
    cartItemCountHandler: CartItemCountHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartItemClickListener = cartItemClickListener
        binding.cartItemCountHandler = cartItemCountHandler
    }

    fun bind(order: Order) {
        binding.order = order
    }
}
