package woowacourse.shopping.presentation.ui.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.ui.cart.CartActionHandler

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        actionHandler: CartActionHandler,
    ) {
        binding.cartItem = cartItem
        binding.actionHandler = actionHandler
    }
}
