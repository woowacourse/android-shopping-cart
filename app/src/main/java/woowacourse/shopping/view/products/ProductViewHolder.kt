package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productClickListener: (Product) -> Unit,
    private val quantitySelectListener: () -> Boolean,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Product,
        quantity: Int,
    ) {
        binding.product = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
        binding.btnQuantitySelect.setOnClickListener {
            val isVisible = quantitySelectListener()
            binding.visible = isVisible
        }
        val quantityBinding = binding.viewQuantitySelect
        quantityBinding.productId = item.id
        quantityBinding.quantity = quantity
        quantityBinding.quantitySelectButtonListener = quantitySelectButtonListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productClickListener: (Product) -> Unit,
            quantitySelectListener: () -> Boolean,
            quantitySelectButtonListener: QuantitySelectButtonListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(
                binding,
                productClickListener,
                quantitySelectListener,
                quantitySelectButtonListener,
            )
        }
    }
}
