package woowacourse.shopping.ui.shopping.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.ui.model.UiProduct

class RecentProductsViewHolder(
    binding: ItemRecentProductsBinding,
    onItemClick: (UiProduct) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter: RecentProductAdapter

    init {
        adapter = RecentProductAdapter(onItemClick)
        binding.rvRecentProduct.adapter = adapter
    }

    fun updateRecentProducts(recentProducts: List<UiProduct>) {
        adapter.submitList(recentProducts)
    }
}
