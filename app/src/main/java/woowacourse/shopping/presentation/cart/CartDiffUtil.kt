package woowacourse.shopping.presentation.cart

import androidx.recyclerview.widget.DiffUtil
import woowacourse.shopping.data.model.cart.CartedProduct

object CartDiffUtil : DiffUtil.ItemCallback<CartedProduct>() {
    override fun areItemsTheSame(oldItem: CartedProduct, newItem: CartedProduct): Boolean {
        return oldItem.cartItem.id == newItem.cartItem.id
    }

    override fun areContentsTheSame(oldItem: CartedProduct, newItem: CartedProduct): Boolean {
        return oldItem == newItem
    }
}
