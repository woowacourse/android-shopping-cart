package woowacourse.shopping.ui.products.recent

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductViewHolder(private val binding: ItemRecentProductBinding): ViewHolder(binding.root) {
    fun bind(recentProductUiModel: RecentProductUiModel) {
        binding.recentProduct = recentProductUiModel
    }
}
