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
    private val openQuantitySelectListener: () -> Boolean,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CartItem) {
        binding.cartItem = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
        binding.visible = openQuantitySelectListener()
        binding.viewQuantitySelect.root.visibility = View.VISIBLE

        binding.btnQuantitySelect.setOnClickListener {
            binding.visible = openQuantitySelectListener()
        }
        val quantityBinding = binding.viewQuantitySelect
        quantityBinding.productId = item.product.id
        quantityBinding.tvProductQuantity.text = item.quantity.toString()
        quantityBinding.quantitySelectButtonListener = quantitySelectButtonListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productClickListener: (CartItem) -> Unit,
            openQuantitySelectListener: () -> Boolean,
            quantitySelectButtonListener: QuantitySelectButtonListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(
                binding,
                productClickListener,
                openQuantitySelectListener,
                quantitySelectButtonListener,
            )
        }
    }
}
