package woowacourse.shopping.view.productlist.recentviewed

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.ProductListAdapter

class RecentViewedItemViewHolder(
    private val binding: ItemRecentViewedProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, onItemClick: ProductListAdapter.OnItemClick) {
        binding.product = product
        binding.onItemClick = onItemClick
    }
}
