package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.model.CartProductUIModel

class CartViewHolder private constructor(
    val binding: CartItemBinding,
    private val onCartClickListener: OnCartClickListener,
) : ItemViewHolder(binding.root) {
    fun bind(cart: CartProductUIModel) {
        binding.product = cart
        binding.listener = onCartClickListener

        binding.checkBox.setOnClickListener {
            onCartClickListener.onCheckChanged(cart.product.id, binding.checkBox.isChecked)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onCartClickListener: OnCartClickListener,
        ): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, onCartClickListener)
        }
    }
}
