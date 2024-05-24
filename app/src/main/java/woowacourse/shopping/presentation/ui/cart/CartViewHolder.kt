package woowacourse.shopping.presentation.ui.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        eventHandler: CartEventHandler,
        cartItemCountHandler: CartItemCountHandler,
    ) {
        binding.cartItem = cartItem
        binding.eventHandler = eventHandler
        binding.countHandler = cartItemCountHandler
    }
}
