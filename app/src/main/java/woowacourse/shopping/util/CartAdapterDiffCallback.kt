package woowacourse.shopping.util

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.cart.CartAdapterItem

class CartAdapterDiffCallback : DiffUtil.ItemCallback<CartAdapterItem>() {
    override fun areItemsTheSame(
        oldItem: CartAdapterItem,
        newItem: CartAdapterItem,
    ): Boolean =
        when {
            oldItem is CartAdapterItem.Product && newItem is CartAdapterItem.Product ->
                oldItem.product.id == newItem.product.id
            oldItem is CartAdapterItem.PaginationButton && newItem is CartAdapterItem.PaginationButton ->
                true
            else -> false
        }

    override fun areContentsTheSame(
        oldItem: CartAdapterItem,
        newItem: CartAdapterItem,
    ): Boolean = oldItem == newItem
}
