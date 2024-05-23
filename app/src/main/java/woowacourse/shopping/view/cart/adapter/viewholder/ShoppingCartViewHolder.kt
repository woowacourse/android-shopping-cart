package woowacourse.shopping.view.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.cart.ShoppingCartActionHandler

class ShoppingCartViewHolder(
    private val binding: ItemShoppingCartBinding,
    private val shoppingCartActionHandler: ShoppingCartActionHandler,
    private val countActionHandler: CountActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
        binding.shoppingCartActionHandler = shoppingCartActionHandler
        binding.countActionHandler = countActionHandler
    }
}
