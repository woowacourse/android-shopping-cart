package woowacourse.shopping.presentation.ui.shopping.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class RecentlyProductViewHolder(private val binding: ItemRecentlyProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        recentlyViewedProduct: RecentlyViewedProduct,
        actionHandler: ShoppingActionHandler,
    ) {
        binding.recentlyViewedProduct = recentlyViewedProduct
        binding.actionHandler = actionHandler
    }
}
