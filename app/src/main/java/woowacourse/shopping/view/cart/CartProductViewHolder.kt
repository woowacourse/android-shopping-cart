package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartItem

class CartProductViewHolder(
    val binding: ItemCartProductBinding,
    onDeleteClick: (CartItem, Int) -> Unit,
    val onIncreaseClick: (CartItem) -> Unit,
    val onDecreaseClick: (CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toDelete = onDeleteClick
        binding.increase = { binding.cartItem?.let { onIncreaseClick(it) } }
        binding.decrease = { binding.cartItem?.let { onDecreaseClick(it) } }
    }

    fun bind(
        cartItem: CartItem,
        position: Int,
    ) {
        binding.cartItem = cartItem
        binding.position = position
        binding.executePendingBindings()
    }
}
