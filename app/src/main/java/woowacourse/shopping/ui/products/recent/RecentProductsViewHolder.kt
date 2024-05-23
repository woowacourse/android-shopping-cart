package woowacourse.shopping.ui.products.recent

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemRecentProductsBinding

class RecentProductsViewHolder(private val binding: ItemRecentProductsBinding) :
    ViewHolder(binding.root) {
    private val adapter: RecentProductsAdapter = RecentProductsAdapter()

    init {
        binding.rvRecentProduct.adapter = adapter
    }

    fun bind(recentProducts: List<RecentProductUiModel>) {
        adapter.insertRecentProduct(recentProducts)
    }
}
