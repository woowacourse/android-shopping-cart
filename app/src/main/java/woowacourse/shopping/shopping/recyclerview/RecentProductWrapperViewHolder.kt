package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ListRecentProductLayoutBinding

class RecentProductWrapperViewHolder(
    private val binding: ListRecentProductLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(adapter: RecentProductAdapter) {
        binding.shoppingRecentProductList.adapter = adapter
    }
}
