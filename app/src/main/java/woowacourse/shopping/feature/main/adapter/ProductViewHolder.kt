package woowacourse.shopping.feature.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.cart.adapter.OnClickMinusButton
import woowacourse.shopping.feature.cart.adapter.OnClickPlusButton
import woowacourse.shopping.common.QuantityControlListener
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.Product

class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        onClickProductItem: OnClickProductItem,
        onClickPlusButton: OnClickPlusButton,
        onClickMinusButton: OnClickMinusButton,
        product: Product,
        cartItemQuantity: CartItemQuantity,
        updateQuantity: () -> Unit,
    ) {
        binding.product = product
        binding.cartItemQuantity = cartItemQuantity
        binding.root.setOnClickListener {
            onClickProductItem(product.id)
        }
        binding.quantityControlListener =
            object : QuantityControlListener {
                override fun addProduct() {
                    onClickPlusButton(product.id)
                    updateQuantity()
                }

                override fun deleteProduct() {
                    onClickMinusButton(product.id)
                    updateQuantity()
                }
            }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
