package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener

class CartViewHolder private constructor(
    private val binding: ItemCartBinding,
    cartListener: CartListener
) : CartItemViewHolder(binding.root) {
    init {
        binding.listener = cartListener
    }
    override fun bind(data: CartItemType) {
        if (data !is CartItemType.Cart) return
        binding.product = data.product
        binding.tvProductCount.text = 0.toString()
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): CartViewHolder {
            val binding = ItemCartBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, cartListener)
        }
    }
}
