package woowacourse.shopping.view.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.products.ShoppingCartItem

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
    private val onProductRemove: (String) -> Unit,
    private val onQuantityChange: (String, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ShoppingCartItem) {
        binding.product = item.product

        binding.quantityControl.tvQuantity.text = item.quantity.toString()

        binding.onProductRemoveClickListener =
            object : OnProductRemoveClickListener {
                override fun onRemoveClick(productId: String) {
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
