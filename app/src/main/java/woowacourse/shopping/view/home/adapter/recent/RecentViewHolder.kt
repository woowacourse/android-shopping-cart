package woowacourse.shopping.view.home.adapter.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.home.HomeClickListener

class RecentViewHolder(private val binding: ItemRecentViewedProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        clickListener: HomeClickListener,
    ) {
        binding.product = product
        binding.clickListener = clickListener
    }
}
