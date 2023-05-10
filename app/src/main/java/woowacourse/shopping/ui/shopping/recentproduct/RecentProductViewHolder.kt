package woowacourse.shopping.ui.shopping.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.ui.model.UiProduct

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
    onItemClick: (UiProduct) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var product: UiProduct

    init {
        binding.root.setOnClickListener { onItemClick(product) }
    }

    fun bind(item: UiProduct) {
        product = item
        binding.product = item
    }
}
