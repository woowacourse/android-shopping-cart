package woowacourse.shopping.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartNavigationItemBinding

class CartNavigationViewHolder private constructor(
    binding: CartNavigationItemBinding,
    onPageUp: () -> Unit,
    onPageDown: () -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.pageUp.setOnClickListener { onPageUp() }
        binding.pageDown.setOnClickListener { onPageDown() }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onPageUp: () -> Unit,
            onPageDown: () -> Unit
        ): CartNavigationViewHolder {
            val binding = CartNavigationItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartNavigationViewHolder(binding, onPageUp, onPageDown)
        }
    }
}
