package woowacourse.shopping.ui.shopping.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductsBinding

class RecentProductsViewHolder(
    private val binding: ItemRecentProductsBinding,
    recentProductAdapter: RecentProductAdapter
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.rvRecentProduct.adapter = recentProductAdapter
    }

    fun bind(visibility: Boolean) {
        binding.isVisible = visibility
    }
}
