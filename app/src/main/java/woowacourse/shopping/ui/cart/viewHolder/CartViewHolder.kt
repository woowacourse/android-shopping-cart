package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.ui.cart.CartItemType
import woowacourse.shopping.ui.cart.CartListener

class CartViewHolder private constructor(
    private val binding: CartItemBinding,
    private val cartListener: CartListener
) : CartItemViewHolder(binding.root) {
    override fun bind(cartItemType: CartItemType) {
        val cart = cartItemType as? CartItemType.Cart ?: return
        binding.product = cart.product
        binding.root.setOnClickListener { cartListener.onItemClick(cart.product) }
        binding.removeButton.setOnClickListener { cartListener.onItemRemove(cart.product.id) }
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, cartListener)
        }
    }
}
