package woowacourse.shopping.presentation.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.RecentProduct

class RecentViewHolder(
    private val binding: ItemRecentProductBinding,
    private val onClickProducts: ShoppingEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProduct) {
        binding.recentProduct = recentProduct
        binding.onClickProduct = onClickProducts
    }
}
