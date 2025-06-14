package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.ShoppingCartItem

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
    private val onProductRemove: (Int) -> Unit,
    private val onQuantityChange: (Int, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ShoppingCartItem) {
        binding.model = item

        binding.onProductRemoveClickListener =
            object : OnProductRemoveClickListener {
                override fun onRemoveClick(productId: Int) {
                    onProductRemove(productId)
                }
            }
        binding.quantityControl.tvPlus.setOnClickListener {
            onQuantityChange(item.product.id, item.quantity + 1)
        }

        binding.quantityControl.tvMinus.setOnClickListener {
            if (item.quantity > 1) {
                onQuantityChange(item.product.id, item.quantity - 1)
            }
        }
    }
}
