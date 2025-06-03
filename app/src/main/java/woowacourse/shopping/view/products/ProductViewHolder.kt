package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.cart.CartItem

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productEventListener: ProductEventListener,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: CartItem,
        isOpened: Boolean,
    ) {
        binding.cartItem = item
        binding.productEventListener = productEventListener
        binding.isSelectorVisible = isOpened

        bindQuantitySelector(item)
    }

    private fun bindQuantitySelector(item: CartItem) {
        val quantityBinding = binding.viewQuantitySelect
        quantityBinding.productId = item.product.id
        quantityBinding.quantitySelectButtonListener = quantitySelectButtonListener
        quantityBinding.quantity = item.quantity
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productEventListener: ProductEventListener,
            quantitySelectButtonListener: QuantitySelectButtonListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(
                binding = binding,
                productEventListener,
                quantitySelectButtonListener,
            )
        }
    }
}
