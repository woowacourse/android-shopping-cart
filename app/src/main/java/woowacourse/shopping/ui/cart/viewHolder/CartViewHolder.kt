package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartItem

class CartViewHolder private constructor(
    val binding: CartItemBinding,
    val onClick: (ProductUIModel) -> Unit,
    val onRemove: (Int) -> Unit,
) : ItemViewHolder(binding.root) {
    fun bind(cart: CartItem) {
        binding.product = cart.product
        binding.root.setOnClickListener { onClick(cart.product) }
        binding.removeButton.setOnClickListener { onRemove(cart.product.id) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (ProductUIModel) -> Unit,
            onRemove: (Int) -> Unit,
        ): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, onClick, onRemove)
        }
    }
}
