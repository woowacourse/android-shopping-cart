package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener

class CartViewHolder private constructor(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener
) : CartItemViewHolder(binding.root) {
    override fun bind(cartItemType: CartItemType) {
        val cart = cartItemType as? CartItemType.Cart ?: return
        binding.product = cart.product
        binding.root.setOnClickListener { cartListener.onItemClick(cart.product) }
        binding.ivRemove.setOnClickListener { cartListener.onItemRemove(cart.product.id) }
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): CartViewHolder {
            val binding = ItemCartBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, cartListener)
        }
    }
}
