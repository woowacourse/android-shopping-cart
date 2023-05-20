package woowacourse.shopping.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.model.CartProductUIModel

class CartViewHolder private constructor(
    val binding: CartItemBinding,
    val onClick: (CartProductUIModel) -> Unit,
    val onRemove: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cart: CartItem) {
        binding.cartProduct = cart.cartProduct
        binding.root.setOnClickListener { onClick(cart.cartProduct) }
        binding.removeButton.setOnClickListener { onRemove(cart.cartProduct.product.id) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (CartProductUIModel) -> Unit,
            onRemove: (Int) -> Unit,
        ): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, onClick, onRemove)
        }
    }
}
