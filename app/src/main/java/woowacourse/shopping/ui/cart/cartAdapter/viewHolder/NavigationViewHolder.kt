package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartNavigationItemBinding
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener

class NavigationViewHolder private constructor(
    private val binding: CartNavigationItemBinding,
    private val cartListener: CartListener
) : CartItemViewHolder(binding.root) {
    init {
        binding.pageUp.setOnClickListener { cartListener.onPageUp() }
        binding.pageDown.setOnClickListener { cartListener.onPageDown() }
    }

    override fun bind(cartItemType: CartItemType) {
        val cart = (cartItemType as? CartItemType.Navigation ?: return).cart
        binding.cartNavigation.visibility = when {
            cart.pageUp || cart.pageDown -> VISIBLE
            else -> GONE
        }
        binding.cart = cart
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): NavigationViewHolder {
            val binding = CartNavigationItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return NavigationViewHolder(binding, cartListener)
        }
    }
}
