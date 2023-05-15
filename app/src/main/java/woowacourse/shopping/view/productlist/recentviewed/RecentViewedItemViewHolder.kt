package woowacourse.shopping.view.productlist.recentviewed

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductModel

class RecentViewedItemViewHolder(
    private val binding: ItemRecentViewedProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, onProductClick: (ProductModel) -> Unit) {
        binding.product = product
        binding.onItemClick = onProductClick
    }
}
