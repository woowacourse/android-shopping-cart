package woowacourse.shopping.view.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.view.cart.CartItemClickListener
import woowacourse.shopping.view.cart.QuantityClickListener

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        cartItemClickListener: CartItemClickListener,
        quantityClickListener: QuantityClickListener,
    ) {
        binding.cartItem = cartItem
        binding.cartItemClickListener = cartItemClickListener
        binding.quantityClickListener = quantityClickListener
    }
}
