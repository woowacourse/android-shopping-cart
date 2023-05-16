package woowacourse.shopping.feature.main.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.RecentProductUiModel

class RecentViewHolder(
    private val binding: ItemRecentProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        recentProduct: RecentProductUiModel,
        onClick: (recentProduct: RecentProductUiModel) -> Unit
    ) {
        binding.recentProduct = recentProduct
        binding.recentProductLayout.setOnClickListener {
            onClick.invoke(recentProduct)
        }
    }
}
