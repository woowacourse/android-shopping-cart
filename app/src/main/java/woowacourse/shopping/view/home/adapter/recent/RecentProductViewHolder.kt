package woowacourse.shopping.view.home.adapter.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.view.home.HomeClickListener

class RecentProductViewHolder(private val binding: ItemRecentProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        recentProduct: RecentProduct,
        clickListener: HomeClickListener,
    ) {
        binding.recentProduct = recentProduct
        binding.clickListener = clickListener
    }
}
