package woowacourse.shopping.feature.cart.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.feature.main.QuantityControlListener
import woowacourse.shopping.model.CartItem

class CartViewHolder(
    private val binding: ItemCartBinding,
) : ViewHolder(binding.root) {
    fun bind(
        onClickExit: OnClickExit,
        onClickPlusButton: OnClickPlusButton,
        onClickMinusButton: OnClickMinusButton,
        cartItem: CartItem,
    ) {
        binding.cartItem = cartItem
        binding.ivCartExit.setOnClickListener {
            onClickExit(cartItem.product.id)
        }
        binding.quantityControlListener =
            object : QuantityControlListener {
                override fun addProduct() {
                    onClickPlusButton(cartItem.product.id)
                }

                override fun deleteProduct() {
                    onClickMinusButton(cartItem.product.id)
                }
            }
    }
}

typealias OnClickExit = (cartItemId: Long) -> Unit
