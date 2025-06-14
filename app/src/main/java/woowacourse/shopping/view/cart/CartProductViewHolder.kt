package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartItem

class CartProductViewHolder(
    val binding: ItemCartProductBinding,
    onDeleteClick: (CartItem, Int) -> Unit,
    onIncreaseClick: (CartItem) -> Unit,
    onDecreaseClick: (CartItem) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toDelete = onDeleteClick
        binding.increase = onIncreaseClick
        binding.decrease = onDecreaseClick
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
