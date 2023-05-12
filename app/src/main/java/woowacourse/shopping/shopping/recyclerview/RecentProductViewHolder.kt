package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductViewHolder(
    private val binding: ItemRecentProductListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProductModel) {
        binding.recentProduct = recentProduct
    }
}
