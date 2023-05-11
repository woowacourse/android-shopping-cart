package woowacourse.shopping.presentation.productlist.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding

class RecentProductContainerViewHolder(
    binding: ItemRecentProductContainerBinding,
    recentProductAdapter: RecentProductAdapter,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.recyclerRecentProduct.adapter = recentProductAdapter
    }
}
