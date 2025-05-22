package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.domain.CartItem

class CartProductViewHolder(
    val binding: ItemCartProductBinding,
    val onDeleteClick: (CartItem, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toDelete = onDeleteClick
    }

    fun bind(
        cartItem: CartItem,
        position: Int,
    ) {
        binding.cartItem = cartItem
        binding.position = position
    }
}
