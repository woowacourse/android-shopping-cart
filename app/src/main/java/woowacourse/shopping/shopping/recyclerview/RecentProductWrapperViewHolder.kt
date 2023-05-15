package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductListWrapperBinding

class RecentProductWrapperViewHolder(
    private val binding: ItemRecentProductListWrapperBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(adapter: RecentProductAdapter) {
        binding.shoppingRecentProductList.adapter = adapter
    }
}
