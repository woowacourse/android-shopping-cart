package woowacourse.shopping.presentation.cart

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.domain.model.CartItem

class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(
        oldItem: CartItem,
        newItem: CartItem,
    ): Boolean = oldItem.product.productId == newItem.product.productId

    override fun areContentsTheSame(
        oldItem: CartItem,
        newItem: CartItem,
    ): Boolean = oldItem == newItem
}
