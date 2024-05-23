package woowacourse.shopping.presentation.shopping.product.adpater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentWrapperBinding

class RecentProductViewHolderWrapper(private val binding: ItemRecentWrapperBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(adapter: RecentProductAdapter) {
        binding.rvRecentProductList.adapter = adapter
    }
}
