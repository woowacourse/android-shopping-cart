package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemCartNavigationBinding
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener

class NavigationViewHolder private constructor(
    private val binding: ItemCartNavigationBinding,
    cartListener: CartListener
) : CartViewHolder(binding.root) {
    init {
        binding.listener = cartListener
    }

    fun bind(data: CartItemType.Navigation) {
        binding.cart = data.cart
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): NavigationViewHolder {
            val binding = ItemCartNavigationBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return NavigationViewHolder(binding, cartListener)
        }
    }
}
