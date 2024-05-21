package woowacourse.shopping.view.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.OnClickShoppingCart

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartBinding,
    private val onClickCartItemCounter: OnClickCartItemCounter,
    private val onClickShoppingCart: OnClickShoppingCart,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        position: Int,
    ) {
        binding.cartItem = cartItem
        binding.position = position
        binding.onClickCartItemCounter = onClickCartItemCounter
        binding.onClickShoppingCart = onClickShoppingCart
    }
}
