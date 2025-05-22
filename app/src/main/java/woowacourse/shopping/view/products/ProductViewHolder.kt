package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.product.Product

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val productClickListener: (Product) -> Unit,
    private val openQuantitySelectListener: () -> Boolean,
    private val quantitySelectButtonListener: QuantitySelectButtonListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Product,
        quantity: Int,
        isQuantitySelectorOpened: Boolean,
    ) {
        binding.product = item
        binding.btnSelectedProduct.setOnClickListener {
            productClickListener(item)
        }
        binding.visible = !openQuantitySelectListener() || isQuantitySelectorOpened
        binding.btnQuantitySelect.setOnClickListener {
            binding.visible = openQuantitySelectListener()
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
