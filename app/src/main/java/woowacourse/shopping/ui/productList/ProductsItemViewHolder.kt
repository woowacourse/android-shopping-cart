package woowacourse.shopping.ui.productList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.ProductData
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.ProductIdsCount

class ProductsItemViewHolder(
    private val binding: HolderProductBinding,
    private val onProductItemClickListener: ProductRecyclerViewAdapter.OnProductItemClickListener,
    private val onItemQuantityChangeListener: ProductRecyclerViewAdapter.OnItemQuantityChangeListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: ProductData,
        productIdsCount: List<ProductIdsCount>,
    ) {
        binding.product = product
        productIdsCount.find { it.productId == product.id }?.let {
            binding.quantity = it.quantity
        }
        binding.onProductItemClickListener = onProductItemClickListener
        binding.onItemChargeListener = onItemQuantityChangeListener
    }
}
