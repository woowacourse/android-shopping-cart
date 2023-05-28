package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartNavigationItemBinding
import woowacourse.shopping.model.CartUIModel

class CartNavigationViewHolder private constructor(
    private val binding: CartNavigationItemBinding,
    onPageUp: () -> Unit,
    onPageDown: () -> Unit,
) : ItemViewHolder(binding.root) {
    init {
        binding.pageUp.setOnClickListener { onPageUp() }
        binding.pageDown.setOnClickListener { onPageDown() }
    }

    fun bind(cart: CartUIModel) {
        binding.cartNavigation.visibility = when {
            cart.pageUp -> VISIBLE
            cart.pageDown -> VISIBLE
            else -> GONE
        }
        binding.cart = cart
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onPageUp: () -> Unit,
            onPageDown: () -> Unit,
        ): CartNavigationViewHolder {
            val binding = CartNavigationItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartNavigationViewHolder(binding, onPageUp, onPageDown)
        }
    }
}
