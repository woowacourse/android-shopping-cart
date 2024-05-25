package woowacourse.shopping.ui.products.adapter.recent

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.databinding.ItemRecentProductBinding

class RecentProductViewHolder(private val binding: ItemRecentProductBinding) :
    ViewHolder(binding.root) {
    fun bind(
        recentProductUiModel: RecentProductUiModel,
        onClickRecentProductItem: OnClickRecentProductItem,
    ) {
        binding.root.setOnClickListener { onClickRecentProductItem(recentProductUiModel.productId) }
        binding.recentProduct = recentProductUiModel
    }
}

typealias OnClickRecentProductItem = (productId: Long) -> Unit
