package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.cart.CartItem

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productClickListener: (CartItem) -> Unit,
    private val openQuantitySelectListener: (CartItem) -> Unit,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
    private val openedSelectorItems: MutableList<Long>,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CartItem) {
        binding.cartItem = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
        bindOpenQuantitySelectorButton(item)
        bindQuantitySelector(item)
    }

    private fun bindQuantitySelector(item: CartItem) {
        val quantityBinding = binding.viewQuantitySelect
        quantityBinding.productId = item.product.id
        quantityBinding.tvProductQuantity.text = item.quantity.toString()
        quantityBinding.quantitySelectButtonListener = quantitySelectButtonListener
    }

    private fun bindOpenQuantitySelectorButton(item: CartItem) {
        val isOpened = openedSelectorItems.contains(item.product.id)
        val quantitySelector = binding.viewQuantitySelect.root
        val openQuantitySelectorButton = binding.btnQuantitySelect

        quantitySelector.visibility = if (!isOpened) View.GONE else View.VISIBLE
        openQuantitySelectorButton.visibility = if (!isOpened) View.VISIBLE else View.GONE

        openQuantitySelectorButton.setOnClickListener {
            openQuantitySelectListener(item)
            openQuantitySelectorButton.visibility = View.GONE
            quantitySelector.visibility = View.VISIBLE
            openedSelectorItems.add(item.product.id)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productClickListener: (CartItem) -> Unit,
            openQuantitySelectListener: (CartItem) -> Unit,
            quantitySelectButtonListener: QuantitySelectButtonListener,
            openedSelectorItems: MutableList<Long>,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(
                binding,
                productClickListener,
                openQuantitySelectListener,
                quantitySelectButtonListener,
                openedSelectorItems,
            )
        }
    }
}
