package woowacourse.shopping.ui.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.ui.model.RecentProductModel

class RecentProductViewHolder(
    private val binding: ItemRecentProductListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProductModel) {
        binding.recentProduct = recentProduct
    }
}
