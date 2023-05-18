package woowacourse.shopping.ui.cart.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.CartItemBinding
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.ProductUIModel

class CartViewHolder private constructor(
    val binding: CartItemBinding,
    val onClick: (ProductUIModel) -> Unit,
    val onRemove: (Long) -> Unit,
    val onCountChanged: (Long, Int) -> Unit,
    val onCheckChanged: (Long, Boolean) -> Unit,
) : ItemViewHolder(binding.root) {
    fun bind(cart: CartProductUIModel) {
        binding.product = cart
        binding.root.setOnClickListener { onClick(cart.product) }
        binding.removeButton.setOnClickListener { onRemove(cart.product.id) }
        binding.orderCountLayout.minusBtn.setOnClickListener {
            cart.count.set(cart.count.get() - 1)
            onCountChanged(cart.product.id, cart.count.get())
        }
        binding.orderCountLayout.plusBtn.setOnClickListener {
            cart.count.set(cart.count.get() + 1)
            onCountChanged(cart.product.id, cart.count.get())
        }
        binding.checkBox.setOnClickListener {
            cart.isChecked.set(cart.isChecked.get().not())
            onCheckChanged(cart.product.id, cart.isChecked.get())
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (ProductUIModel) -> Unit,
            onRemove: (Long) -> Unit,
            onCountChanged: (Long, Int) -> Unit,
            onCheckChanged: (Long, Boolean) -> Unit,
        ): CartViewHolder {
            val binding = CartItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, onClick, onRemove, onCountChanged, onCheckChanged)
        }
    }
}
