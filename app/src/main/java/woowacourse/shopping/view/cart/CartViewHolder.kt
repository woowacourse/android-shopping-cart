package woowacourse.shopping.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductInCartBinding
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.products.QuantitySelectButtonListener

class CartViewHolder(
    private val binding: ItemProductInCartBinding,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.ViewHolder(binding.root) {
    val removeProductButton = binding.removeProductBtn

    fun bind(item: CartItem) {
        binding.cartItem = item

        val quantityBinding = binding.viewQuantitySelect
        quantityBinding.productId = item.product.id
        quantityBinding.quantity = item.quantity
        quantityBinding.quantitySelectButtonListener = quantitySelectButtonListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            quantitySelectButtonListener: QuantitySelectButtonListener,
        ): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductInCartBinding.inflate(inflater, parent, false)
            return CartViewHolder(binding, quantitySelectButtonListener)
        }
    }
}
