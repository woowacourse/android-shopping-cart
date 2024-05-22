package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.CartItemClickListener

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        clickListener: CartItemClickListener,
    ) {
        binding.cartItem = cartItem
        binding.clickListener = clickListener
    }
}
