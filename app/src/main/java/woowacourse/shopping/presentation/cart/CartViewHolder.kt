package woowacourse.shopping.presentation.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.CartItem

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cartItem: CartItem,
        clickListener: CartClickListener,
    ) {
        binding.cartItem = cartItem
        binding.clickListener = clickListener
    }
}
