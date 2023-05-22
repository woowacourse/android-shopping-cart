package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ProductsViewHolder private constructor(
    private val binding: ItemProductBinding,
    private val listener: ProductsListener
) : ShoppingViewHolder(binding.root) {
    init {
        binding.listener = listener
    }

    override fun bind(productItemType: ProductsItemType) {
        val productItem = productItemType as? ProductsItemType.Product ?: return

        binding.item = productItem

        binding.btnProductCount.setOnCountChangeListener { _, count ->
            listener.onAddCartOrUpdateCount(productItem.product.id, count)
            if (count == 0) {
                binding.btnAddToCart.visibility = View.VISIBLE
                binding.btnProductCount.visibility = View.GONE
            } else {
                binding.btnAddToCart.visibility = View.GONE
                binding.btnProductCount.visibility = View.VISIBLE
            }
        }
        binding.btnAddToCart.setOnClickListener {
            binding.btnProductCount.count = 1
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: ProductsListener): ProductsViewHolder {
            val binding = ItemProductBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductsViewHolder(binding, listener)
        }
    }
}
