package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.RecentProduct

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecentProduct) {
        binding.product = item.product
    }
}
