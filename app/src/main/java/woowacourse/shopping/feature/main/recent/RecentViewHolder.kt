package woowacourse.shopping.feature.main.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentViewHolder(
    private val binding: ItemRecentProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProductItemModel) {
        binding.itemModel = recentProduct
        binding.position = bindingAdapterPosition
    }
}
