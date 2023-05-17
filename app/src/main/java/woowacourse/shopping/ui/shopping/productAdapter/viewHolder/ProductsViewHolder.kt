package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ProductsViewHolder private constructor(
    private val binding: ItemProductBinding,
    private val listener: ProductsListener
) : ItemViewHolder(binding.root) {
    init {
        binding.listener = listener
    }

    override fun bind(productItemType: ProductsItemType) {
        val productItem = productItemType as? ProductsItemType.Product ?: return

        val onCountChange = { it: Int ->
            listener.onAddCartOrUpdateCount(productItem.product.id, it) {
                bind(productItem.copy(count = it))
            }
        }

        binding.item = productItem
        binding.onCountChange = onCountChange
    }

    companion object {
        fun from(parent: ViewGroup, listener: ProductsListener): ProductsViewHolder {
            val binding = ItemProductBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductsViewHolder(binding, listener)
        }
    }
}
